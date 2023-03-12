package com.kristian.socmed.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kristian.socmed.model.entity.Following;
import com.kristian.socmed.model.entity.FollowingId;

public interface FollowRepository extends JpaRepository<Following, FollowingId> {
  List<Following> findAllByFollowedUserId(Long id);
}
