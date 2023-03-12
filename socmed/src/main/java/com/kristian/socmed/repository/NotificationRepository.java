package com.kristian.socmed.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kristian.socmed.model.entity.MyEntity;
import com.kristian.socmed.model.entity.Notification;
import com.kristian.socmed.model.entity.NotificationType;
import com.kristian.socmed.model.entity.Post;
import com.kristian.socmed.model.entity.User;

public interface NotificationRepository extends MyRepository, JpaRepository<Notification, Long> {

  @Override
  default void deleteByParent(MyEntity parent) {
    deleteAllByPost((Post) parent);
  }

  public void deleteAllByPost(Post post);

  Optional<Notification> findTopByToUser_usernameOrderByIdDesc(String username);

  List<Notification> findByToUser_usernameOrderByIdDesc(String username);

  Optional<Notification> findByFromUserAndToUserAndPostAndNotificationType(User fromUser, User toUser, Post post,
      NotificationType notificationType);

  Optional<Notification> findByFromUserAndToUserAndPost(User fromUser, User toUser, Post post);
}
