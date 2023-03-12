package com.kristian.socmed.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kristian.socmed.model.entity.Post;
import com.kristian.socmed.model.entity.Topic;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findByTopicAndDeletebByAdminIsNull(Topic topic);

  List<Post> findAllByUser_usernameAndDeletebByAdminIsNull(String username);

  List<Post> findByUser_userIdInAndDeletebByAdminIsNull(List<Long> following);

  List<Post> findByTopic_nameAndDeletebByAdminIsNull(String topicName);
}
