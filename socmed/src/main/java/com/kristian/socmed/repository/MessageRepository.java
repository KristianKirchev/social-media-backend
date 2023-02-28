package com.kristian.socmed.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kristian.socmed.model.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findTopByFromUser_usernameAndToUser_usernameOrderByIdDesc(String from, String to);
    
    List<Message> findByToUser_usernameAndFromUser_username(String from, String to);
    
    List<Message> findByToUser_usernameOrFromUser_usernameOrderByIdDesc(String from, String to);
    
    int countByToUser_usernameAndFromUser_usernameAndSeenAt(String username, String with, Instant seenAt);
    
    Integer countByToUser_usernameAndSeenAt(String username, Instant seenAt);
    
    List<Message> findByFromUser_username(String username);
    
    List<Message> findByFromUser_usernameAndSeenAt(String username, Instant seenAt);
}
