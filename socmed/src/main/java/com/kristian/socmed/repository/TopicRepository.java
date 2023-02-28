package com.kristian.socmed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kristian.socmed.model.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> getByName(String topicName);
}
