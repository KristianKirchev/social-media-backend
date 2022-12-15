package com.kristian.socmed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kristian.socmed.model.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser_usernameAndDeletebByAdminIsNull(String username);
    List<Post> findByUser_userIdInAndDeletebByAdminIsNull(List<Long> following);
}
