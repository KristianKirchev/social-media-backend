package com.kristian.socmed.service.mapper;

import java.time.ZoneId;

import org.springframework.stereotype.Component;

import com.kristian.socmed.model.entity.Message;
import com.kristian.socmed.model.entity.User;
import com.kristian.socmed.repository.MessageRepository;
import com.kristian.socmed.service.AuthService;
import com.kristian.socmed.service.dto.InboxMessageDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class InboxMessageMapper implements GenericMapper<InboxMessageDto, Message> {

    private AuthService authService;
    private MessageRepository messageRepository;

    @Override
    public Message toEntity(InboxMessageDto dto) {
        return null;
    }

    @Override
    public InboxMessageDto toDto(Message entity) {
        InboxMessageDto dto = new InboxMessageDto();
        dto.setContent(setContent(entity.getContent()));
        int minute = entity.getSentAt().atZone(ZoneId.of("ECT",ZoneId.SHORT_IDS)).getMinute();
        String min;
        if(minute<10){
            min = "0"+minute;
        }else{
            min = minute+"";
        }
        dto.setTime(entity.getSentAt().atZone(ZoneId.of("ECT",ZoneId.SHORT_IDS)).getHour()+":"+min);
        return dto;
    }

    public InboxMessageDto toDto(Message entity, User currentUser){
        InboxMessageDto dto = toDto(entity);
        String with = setWith(entity, currentUser);
        dto.setWith(with);
        dto.setNewMessages(messageRepository.countByToUser_usernameAndFromUser_usernameAndSeenAt(authService.getCurrentUser().getUsername(),with,null));
        return dto;
    }

    private String setContent(String content) {
        if(content.length()>15){
            String substring = content.substring(0, 15);
            String finalStr = substring.concat("...");
            return finalStr;
        }
        return content;
    }

    private String setWith(Message entity,User current) {
        if(entity.getFromUser().getUsername()==current.getUsername()){
            return entity.getToUser().getUsername();
        }
        return entity.getFromUser().getUsername();
    }
}
