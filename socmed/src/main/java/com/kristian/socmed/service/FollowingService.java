package com.kristian.socmed.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kristian.socmed.model.entity.Following;
import com.kristian.socmed.model.entity.User;
import com.kristian.socmed.repository.FollowRepository;
import com.kristian.socmed.service.dto.UserDto;
import com.kristian.socmed.service.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowingService {
  private final FollowRepository followRepository;
  private final UserMapper userMapper;

  @Transactional
  public List<UserDto> getFollowersForUser(Long userId) {
    List<Following> optFoll = followRepository.findAllByFollowedUserId(userId);
    List<User> followers = optFoll.stream().map(Following::getFollowing).toList();

    return followers.stream().map(userMapper::toDto).toList();
  }
}
