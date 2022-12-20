package com.kristian.socmed.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kristian.socmed.model.entity.Topic;
import com.kristian.socmed.repository.TopicRepository;
import com.kristian.socmed.service.dto.TopicDto;
import com.kristian.socmed.service.mapper.TopicMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TopicService {
    private TopicRepository topicRepository;
    private TopicMapper topicMapper;

    @Transactional
    public List<TopicDto> getAllTopics(){
        List<Topic> topics = topicRepository.findAll();
        return topics.stream().map((topic)->topicMapper.toDto(topic)).collect(Collectors.toList());
    }

    @Transactional
    public void createTopic(TopicDto dto) {
        Topic topic = topicMapper.toEntity(dto);
        topicRepository.save(topic);
    }
}