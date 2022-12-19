package com.kristian.socmed.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageRepository,Long> {
    Optional<MessageRepository> findTopByFrom_usernameAndTo_usernameOrderByIdDesc(String from,String to);
    List<MessageRepository> findByTo_usernameAndFrom_username(String from, String to);
    List<MessageRepository> findByTo_usernameOrFrom_usernameOrderByIdDesc(String from,String to);
    int countByTo_usernameAndFrom_usernameAndSeenAt(String username, String with, Instant seenAt);
    Integer countByTo_usernameAndSeenAt(String username, Instant seenAt);
    List<MessageRepository> findByFrom_username(String username);
    List<MessageRepository> findByFrom_usernameAndSeenAt(String username,Instant seenAt);
}
