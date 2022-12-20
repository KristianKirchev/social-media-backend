package com.kristian.socmed.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kristian.socmed.service.ReactionService;
import com.kristian.socmed.service.dto.ReactionDto;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/react")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class ReactionController {

    private ReactionService reactionService;
    
	@PostMapping
    public ResponseEntity react(@RequestBody ReactionDto reactionDto){
        reactionService.react(reactionDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}