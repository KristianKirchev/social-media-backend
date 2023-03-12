package com.kristian.socmed.service.mapper;

import java.time.Instant;

import org.ocpsoft.prettytime.PrettyTime;

import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.Comment;
import com.kristian.socmed.repository.PostRepository;
import com.kristian.socmed.service.AuthService;
import com.kristian.socmed.service.dto.CommentDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommentMapper implements GenericMapper<CommentDto, Comment> {

  private PostRepository postRepository;
  private AuthService authService;

  @Override
  public Comment toEntity(CommentDto dto) {
    Comment comment = new Comment();
    comment
        .setPost(postRepository.findById(dto.getPostId()).orElseThrow(() -> new MyRuntimeException("Post not found")));
    comment.setDate(Instant.now());
    comment.setContent(dto.getText());
    comment.setUser(authService.getCurrentUser());
    return comment;
  }

  @Override
  public CommentDto toDto(Comment comment) {
    CommentDto dto = new CommentDto();
    dto.setId(comment.getId());
    dto.setPostId(comment.getPost().getId());
    dto.setUsername(comment.getUser().getUsername());
    dto.setText(comment.getContent());
    PrettyTime p = new PrettyTime();
    dto.setDuration(p.format(comment.getDate()));
    return dto;
  }
}
