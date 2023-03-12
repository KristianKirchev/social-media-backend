package com.kristian.socmed.service.mapper;

import com.kristian.socmed.model.entity.Notification;
import com.kristian.socmed.model.entity.NotificationType;
import com.kristian.socmed.service.dto.NotificationDto;

public class NotificationMapper implements GenericMapper<NotificationDto, Notification> {
  @Override
  public Notification toEntity(NotificationDto dto) {
    return null;
  }

  @Override
  public NotificationDto toDto(Notification entity) {
    NotificationDto dto = new NotificationDto();
    dto.setId(entity.getId());
    dto.setPostId(entity.getPost().getId());
    dto.setMessage(formatMessage(entity));
    dto.setRead(entity.isRead());
    return dto;
  }

  private String formatMessage(Notification entity) {
    if (entity.getNotificationType() == NotificationType.LIKE) {
      return entity.getFromUser().getUsername() + " liked your post";
    } else if (entity.getNotificationType() == NotificationType.DISLIKE) {
      return entity.getFromUser().getUsername() + " disliked your post";
    } else {
      return entity.getFromUser().getUsername() + " commented your post";
    }
  }
}
