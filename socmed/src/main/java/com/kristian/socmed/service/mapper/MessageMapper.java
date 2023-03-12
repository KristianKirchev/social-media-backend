package com.kristian.socmed.service.mapper;

import java.time.Instant;
import java.time.ZoneId;

import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.Message;
import com.kristian.socmed.repository.UserRepository;
import com.kristian.socmed.service.dto.MessageDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageMapper implements GenericMapper<MessageDto, Message> {

  private UserRepository userRepository;

  @Override
  public Message toEntity(MessageDto dto) {
    Message message = new Message();
    message.setContent(dto.getContent());
    message.setFromUser(
        userRepository.findByUsername(dto.getFromUser()).orElseThrow(() -> new MyRuntimeException("User not found")));
    message.setToUser(
        userRepository.findByUsername(dto.getToUser()).orElseThrow(() -> new MyRuntimeException("User not found")));
    message.setSentAt(Instant.now());
    return message;
  }

  @Override
  public MessageDto toDto(Message entity) {
    MessageDto dto = new MessageDto();
    dto.setContent(entity.getContent());
    dto.setFromUser(entity.getFromUser().getUsername());
    dto.setToUser(entity.getToUser().getUsername());
    int minute = entity.getSentAt().atZone(ZoneId.of("ECT", ZoneId.SHORT_IDS)).getMinute();
    String min;
    if (minute < 10) {
      min = "0" + minute;
    } else {
      min = minute + "";
    }
    dto.setTime(entity.getSentAt().atZone(ZoneId.of("ECT", ZoneId.SHORT_IDS)).getHour() + ":" + min);
    dto.setSeen(entity.getSeenAt() == null ? false : true);
    return dto;
  }
}
