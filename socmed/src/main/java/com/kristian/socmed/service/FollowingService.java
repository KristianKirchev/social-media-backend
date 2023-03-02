package com.kristian.socmed.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kristian.socmed.model.entity.Following;
import com.kristian.socmed.model.entity.User;
import com.kristian.socmed.repository.FollowRepository;
import com.kristian.socmed.service.dto.UserDto;
import com.kristian.socmed.service.mapper.UserMapper;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowingService {
	private final FollowRepository followRepository;
    private final UserMapper userMapper;

    @Transactional
    public List<UserDto> getFollowersForUser(Long userId) {
        List<Following> optFoll = followRepository.findAllByFollowed_userId(userId);
        List<User> followers = optFoll.stream().map(Following::getFollowing).collect(Collectors.toList());
        List<UserDto> followersDto= followers.stream().map((userToMap)->userMapper.toDto(userToMap)).collect(Collectors.toList());
        return followersDto;
    }
}
