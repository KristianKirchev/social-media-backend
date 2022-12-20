package com.kristian.socmed.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kristian.socmed.service.CommentService;
import com.kristian.socmed.service.dto.CommentDto;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class CommentController {

    private CommentService commentService;

    @PostMapping
    public ResponseEntity postComment(@RequestBody CommentDto commentDto){
        commentService.comment(commentDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsFormPost(@PathVariable Long postId){
        List<CommentDto> commentDtos = commentService.getAllCommentsForPost(postId);
        return new ResponseEntity<>(commentDtos,HttpStatus.OK);
    }

	@DeleteMapping("/{id}")
    public ResponseEntity deleteCommend(@PathVariable Long id){
        commentService.deleteComment(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}