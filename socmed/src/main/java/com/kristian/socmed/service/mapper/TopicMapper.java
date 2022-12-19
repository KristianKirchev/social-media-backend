package com.kristian.socmed.service.mapper;

import java.time.Instant;

import com.kristian.socmed.model.entity.Topic;
import com.kristian.socmed.repository.PostRepository;
import com.kristian.socmed.service.AuthService;
import com.kristian.socmed.service.dto.TopicDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TopicMapper implements GenericMapper<TopicDto, Topic> {

    private PostRepository postRepository;
    private AuthService authService;

    @Override
    public Topic toEntity(TopicDto dto) {
        Topic topic = new Topic();
        topic.setDescription(dto.getDescription());
        topic.setName(dto.getName());
        topic.setDate(Instant.now());
        topic.setUser(authService.getCurrentUser());
        
        return topic;
    }

    @Override
    public TopicDto toDto(Topic topic) {
        TopicDto dto = new TopicDto();
        dto.setDescription(topic.getDescription());
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        dto.setNumberOfPosts(postRepository.findByTopicAndDeletebByAdminIsNull(topic).size());
        
        return dto;
    }
}
