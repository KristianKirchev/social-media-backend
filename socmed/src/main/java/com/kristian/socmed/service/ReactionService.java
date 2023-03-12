package com.kristian.socmed.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kristian.socmed.model.entity.Notification;
import com.kristian.socmed.model.entity.Reaction;
import com.kristian.socmed.repository.ReactionRepository;
import com.kristian.socmed.service.dto.ReactionDto;
import com.kristian.socmed.service.mapper.NotificationBuilder;
import com.kristian.socmed.service.mapper.ReactionMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReactionService {

  private ReactionRepository reactionRepository;
  private ReactionMapper reactionMapper;
  private AuthService authService;
  private NotificationService notificationService;
  private NotificationBuilder notificationBuilder;

  @Transactional
  public void react(ReactionDto reactionDto) {
    Optional<Reaction> reactOpt =
        reactionRepository.findByPost_idAndUser(reactionDto.getPostId(), authService.getCurrentUser());
    Reaction reactionEntity = reactionMapper.toEntity(reactionDto);
    if (!reactOpt.isPresent()) {
      reactionRepository.save(reactionEntity);
      Notification not = notificationBuilder.createNotificationForReaction(reactionEntity);
      notificationService.save(not);
      return;
    }
    Reaction reaction = reactOpt.get();
    if (reaction.getReactionType() == reactionDto.getReactionType()) {
      reactionRepository.delete(reaction);
    } else {
      reactionRepository.delete(reaction);
      reactionRepository.save(reactionEntity);
      Notification not = notificationBuilder.createNotificationForReaction(reactionEntity);
      notificationService.save(not);
    }

  }
}
