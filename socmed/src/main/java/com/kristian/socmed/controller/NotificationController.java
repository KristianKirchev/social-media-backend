package com.kristian.socmed.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kristian.socmed.service.NotificationService;
import com.kristian.socmed.service.dto.NotificationDto;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping({"/api/notification", "/api/notification/"})
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class NotificationController {

  private NotificationService notificationService;

  @GetMapping("/last/{username}")
  public ResponseEntity<NotificationDto> getLastNotificationForUser(@PathVariable String username) {
    NotificationDto dto = notificationService.getLastNotificationForUser(username);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<NotificationDto>> getAllNotificationsForUser() {
    List<NotificationDto> list = notificationService.getAllNotificationsForUser();
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PatchMapping("/mark-as-read/{id}")
  public ResponseEntity markAsRead(@PathVariable Long id) {
    notificationService.markAsRead(id);
    return new ResponseEntity(HttpStatus.OK);
  }
}
