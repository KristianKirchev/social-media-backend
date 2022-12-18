package com.kristian.socmed.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WebSocketService {
	private final SimpMessagingTemplate messagingTemplate;
    private AuthService authService;


    public void sendMessage(String username) {
        messagingTemplate.convertAndSend("/topic/" + username, authService.getCurrentUser().getUsername());
    }

    public void sendMessage(String to, String suffix) {
        messagingTemplate.convertAndSendToUser(to, "/topic", "");
    }

    public void sendNotificationToUser(String username,String notificationType){
        messagingTemplate.convertAndSend("/topic/notification/" + username, notificationType);
    }
}
