package com.kristian.socmed.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kristian.socmed.service.TopicService;
import com.kristian.socmed.service.dto.TopicDto;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/topic")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class TopicController {

    private TopicService topicService;

    @GetMapping("/all")
    public ResponseEntity<List<TopicDto>> getAllTopics(){
    return new ResponseEntity<>(topicService.getAllTopics(), HttpStatus.OK);
    }
    
	@CrossOrigin
    @PostMapping("/create")
    public ResponseEntity createTopic(@RequestBody TopicDto topic){
        topicService.createTopic(topic);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public  ResponseEntity<String> handleDataIntegrityViolationException(){
        return new ResponseEntity<>("Topic with given name already exists",HttpStatus.BAD_REQUEST);
    }

}