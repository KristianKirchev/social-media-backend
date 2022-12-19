package com.kristian.socmed.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kristian.socmed.model.entity.MyEntity;
import com.kristian.socmed.model.entity.Post;
import com.kristian.socmed.model.entity.Reaction;
import com.kristian.socmed.model.entity.ReactionType;
import com.kristian.socmed.model.entity.User;



public interface ReactionRepository extends JpaRepository<Reaction,Long>,MyRepository {

    @Override
    default void deleteByParent(MyEntity parent) {
        deleteAllByPost((Post) parent);
    }

    public void deleteAllByPost(Post post);
    List<Reaction> findByPostAndReactionType(Post post, ReactionType like);
    Optional<Reaction> findByPost_idAndUser(Long postId, User currentUser);
}
