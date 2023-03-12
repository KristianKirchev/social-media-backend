package com.kristian.socmed.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.Notification;
import com.kristian.socmed.repository.NotificationRepository;
import com.kristian.socmed.service.dto.NotificationDto;
import com.kristian.socmed.service.mapper.NotificationMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NotificationService {
  private WebSocketService webSocketService;
  private NotificationRepository notificationRepository;
  private NotificationMapper notificationMapper;
  private AuthService authService;

  @Transactional
  public void save(Notification notification) {
    if (!notification.getToUser()
        .getUsername()
        .equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
      Notification notification1 = notificationRepository
          .findByFromUserAndToUserAndPost(notification.getFromUser(), notification.getToUser(), notification.getPost())
          .orElse(notification);
      if (notification.equals(notification1)) {
        notificationRepository.save(notification);
        webSocketService.sendNotificationToUser(notification.getToUser().getUsername(), "notification");
        return;
      }

      if (!notification1.isRead() && notification.getNotificationType().equals(notification1.getNotificationType())) {
        notification1.setDate(Instant.now());
        notificationRepository.save(notification1);
      } else {
        notificationRepository.delete(notification1);
        notificationRepository.save(notification);
        webSocketService.sendNotificationToUser(notification.getToUser().getUsername(), "notification");
      }

    }
  }

  public NotificationDto getLastNotificationForUser(String username) {
    Notification notification = notificationRepository.findTopByToUser_usernameOrderByIdDesc(username)
        .orElseThrow(() -> new MyRuntimeException("Notification not found"));
    return notificationMapper.toDto(notification);
  }

  public List<NotificationDto> getAllNotificationsForUser() {
    List<Notification> notifications =
        notificationRepository.findByToUser_usernameOrderByIdDesc(authService.getCurrentUser().getUsername());
    return notifications.stream()
        .sorted(Comparator.comparing(Notification::getDate).reversed())
        .map(n -> notificationMapper.toDto(n))
        .toList();
  }

  public void markAsRead(Long id) {
    Notification notification =
        notificationRepository.findById(id).orElseThrow(() -> new MyRuntimeException("Notification not found"));
    notification.setRead(true);
    notificationRepository.save(notification);
  }
}
