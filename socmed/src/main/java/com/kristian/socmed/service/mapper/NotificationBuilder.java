package com.kristian.socmed.service.mapper;

import java.time.Instant;

import com.kristian.socmed.model.entity.Comment;
import com.kristian.socmed.model.entity.Notification;
import com.kristian.socmed.model.entity.NotificationType;
import com.kristian.socmed.model.entity.Reaction;
import com.kristian.socmed.model.entity.ReactionType;
import com.kristian.socmed.service.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotificationBuilder {
    public Notification createNotificationForReaction(Reaction react){
        Notification not = new Notification();
        not.setFromUser(react.getUser());
        not.setToUser(react.getPost().getUser());
        not.setDate(Instant.now());
        not.setPost(react.getPost());
        not.setRead(false);
        not.setNotificationType(setNotificationType(react.getReactionType()));
        return not;
    }

    public Notification createNotificationForComment(Comment comment){
        Notification not = new Notification();
        not.setFromUser(comment.getUser());
        not.setToUser(comment.getPost().getUser());
        not.setDate(Instant.now());
        not.setPost(comment.getPost());
        not.setRead(false);
        not.setNotificationType(NotificationType.COMMENT);
        return not;
    }

    private NotificationType setNotificationType(ReactionType reactionType) {
        if(reactionType==ReactionType.LIKE){
            return NotificationType.LIKE;
        }else{
            return NotificationType.DISLIKE;
        }
    }
}
