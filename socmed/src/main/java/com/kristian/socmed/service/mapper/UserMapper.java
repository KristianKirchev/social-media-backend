package com.kristian.socmed.service.mapper;

import com.kristian.socmed.model.entity.User;
import com.kristian.socmed.service.AuthService;
import com.kristian.socmed.service.dto.UserDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserMapper implements GenericMapper<UserDto, User> {

    private AuthService authService;

    @Override
    public User toEntity(UserDto dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setDate(dto.getDate());
        
        return user;
    }

    @Override
    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setDate(user.getDate());
        dto.setEmail(user.getEmail());
        dto.setFollowedByCurrentUser(user.getFollowers().contains(authService.getCurrentUser()));
        dto.setNumOfFollowers(user.getFollowers().size());
        dto.setNumOfFollowing(user.getFollowing().size());
        dto.setMutualFollowers(user.getMutualFollowers(authService.getCurrentUser()));
        dto.setBio(user.getBio());
        
        return dto;
    }
}
