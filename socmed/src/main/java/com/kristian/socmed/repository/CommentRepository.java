package com.kristian.socmed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kristian.socmed.model.entity.Comment;
import com.kristian.socmed.model.entity.MyEntity;
import com.kristian.socmed.model.entity.Post;

public interface CommentRepository  extends MyRepository, JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);

    @Override
    default void deleteByParent(MyEntity parent) {
        deleteAllByPost((Post) parent);
    }

    public void deleteAllByPost(Post post);
    List<Comment> findByPost_idOrderByDateDesc(Long postId);
}
