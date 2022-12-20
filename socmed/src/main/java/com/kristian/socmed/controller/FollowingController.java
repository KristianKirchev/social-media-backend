package com.kristian.socmed.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kristian.socmed.service.FollowingService;
import com.kristian.socmed.service.dto.UserDto;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/following")
@AllArgsConstructor
public class FollowingController {

    private FollowingService followingService;

    @GetMapping("/followers/{userId}")
    public List<UserDto> getFollowersForUser(@PathVariable Long userId){
        return followingService.getFollowersForUser(userId);

    }
}