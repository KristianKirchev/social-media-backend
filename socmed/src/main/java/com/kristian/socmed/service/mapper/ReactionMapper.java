package com.kristian.socmed.service.mapper;

import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.Reaction;
import com.kristian.socmed.repository.PostRepository;
import com.kristian.socmed.service.AuthService;
import com.kristian.socmed.service.dto.ReactionDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReactionMapper implements GenericMapper<ReactionDto, Reaction> {

  private PostRepository postRepository;
  private AuthService authService;

  @Override
  public Reaction toEntity(ReactionDto dto) {
    Reaction reaction = new Reaction();
    reaction
        .setPost(postRepository.findById(dto.getPostId()).orElseThrow(() -> new MyRuntimeException("Post not found")));
    reaction.setReactionType(dto.getReactionType());
    reaction.setUser(authService.getCurrentUser());
    return reaction;
  }

  @Override
  public ReactionDto toDto(Reaction entity) {
    return null;
  }
}
