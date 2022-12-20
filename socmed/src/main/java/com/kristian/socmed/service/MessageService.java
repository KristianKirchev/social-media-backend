package com.kristian.socmed.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.Message;
import com.kristian.socmed.model.entity.User;
import com.kristian.socmed.repository.MessageRepository;
import com.kristian.socmed.service.dto.InboxMessageDto;
import com.kristian.socmed.service.dto.MessageDto;
import com.kristian.socmed.service.mapper.InboxMessageMapper;
import com.kristian.socmed.service.mapper.MessageMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageService {

    private WebSocketService webSocketService;
    private MessageRepository messageRepository;
    private MessageMapper messageMapper;
    private InboxMessageMapper inboxMessageMapper;
    private AuthService authService;
    public void saveMessage(MessageDto messageDto,String id){
        messageRepository.saveAndFlush(messageMapper.toEntity(messageDto));
        webSocketService.sendMessage(id);
        webSocketService.sendNotificationToUser(id,"message");
    }

    public List<MessageDto> getAllMessages(){
        List<Message> all = messageRepository.findAll();
        return all.stream().map(m->messageMapper.toDto(m)).collect(Collectors.toList());
    }

    public MessageDto getLastMesage(String from, String to) {
        Optional<Message> optMessage = messageRepository.findTopByFromUser_usernameAndToUser_usernameOrderByIdDesc(from,to);
        Message message = optMessage.orElseThrow(() -> new MyRuntimeException("Message not found"));
        if(message.getSeenAt()==null){
            message.setSeenAt(Instant.now());
        }
        return messageMapper.toDto(message);
    }

    public List<MessageDto> getAllFromChat(String from, String to) {
        List<Message> messages1 = messageRepository.findByToUser_usernameAndFromUser_username(from,to);
        List<Message> messages2 = messageRepository.findByToUser_usernameAndFromUser_username(to,from);
        messages2.forEach(m->messages1.add(m));

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Message> notSeen = messages1.stream().filter(message -> message.getSeenAt() == null).collect(Collectors.toList());
        notSeen.stream().filter(m->!m.getFromUser().getUsername().equals(currentUser)).forEach(m->m.setSeenAt(Instant.now()));
        messageRepository.saveAll(notSeen);


        messages1.sort(Comparator.comparing(Message::getSentAt));
        return messages1.stream().map(m->messageMapper.toDto(m)).collect(Collectors.toList());
    }

    public List<InboxMessageDto> inboxMessages() {
        User user = authService.getCurrentUser();
        String username = user.getUsername();
        List<Message> messagesOfCurrentUser = messageRepository.findByToUser_usernameOrFromUser_usernameOrderByIdDesc(username, username);
        List<InboxMessageDto> inbox = messagesOfCurrentUser.stream().map(msg -> inboxMessageMapper.toDto(msg,user)).collect(Collectors.toList());
        return inbox.stream().filter(distinctByKey(InboxMessageDto::getWith)).collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public Integer getNewMsgCount() {
        return messageRepository.countByToUser_usernameAndSeenAt(authService.getCurrentUser().getUsername(), null);
    }

    public void readMessagesFrom(String username) {
        List<Message> messages = messageRepository.findByFromUser_usernameAndSeenAt(username,null);
        messages.stream().forEach(m->m.setSeenAt(Instant.now()));
        messageRepository.saveAll(messages);
    }

    public void delete(Long id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new MyRuntimeException("not found"));
        messageRepository.delete(message);

    }
}