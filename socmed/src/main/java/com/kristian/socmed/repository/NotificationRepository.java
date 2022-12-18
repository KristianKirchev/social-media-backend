package com.kristian.socmed.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kristian.socmed.model.entity.MyEntity;
import com.kristian.socmed.model.entity.Notification;
import com.kristian.socmed.model.entity.NotificationType;
import com.kristian.socmed.model.entity.Post;
import com.kristian.socmed.model.entity.User;

public interface NotificationRepository extends JpaRepository<Notification,Long>,MyRepository {

	    @Override
	    default void deleteByParent(MyEntity parent) {
	        deleteAllByPost((Post) parent);
	    }

	    public void deleteAllByPost(Post post);

	    Optional<Notification> findTopByTo_usernameOrderByIdDesc(String username);

	    List<Notification> findByTo_usernameOrderByIdDesc(String username);

	    Optional<Notification> findByFromAndToAndPostAndNotificationType(User fromUser, User toUser, Post post, NotificationType notificationType);

	    Optional<Notification> findByFromAndToAndPost(User fromUser, User toUser, Post post);
}
