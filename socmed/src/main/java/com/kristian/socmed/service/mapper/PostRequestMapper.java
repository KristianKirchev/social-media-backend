package com.kristian.socmed.service.mapper;

import java.time.Instant;

import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.Post;
import com.kristian.socmed.repository.TopicRepository;
import com.kristian.socmed.service.dto.PostRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostRequestMapper implements GenericMapper<PostRequest, Post> {

  private TopicRepository topicRepository;

  @Override
  public Post toEntity(PostRequest dto) {
    Post post = new Post();
    post.setTitle(dto.getTitle());
    post.setContent(dto.getContent());
    post.setTopic(topicRepository.getByName(dto.getTopicName())
        .orElseThrow(() -> new MyRuntimeException("Topic with given name does not exist")));
    post.setDate(Instant.now());
    return post;
  }

  @Override
  public PostRequest toDto(Post entity) {
    return null;
  }
}
