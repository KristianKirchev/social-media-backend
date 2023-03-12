package com.kristian.socmed.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.User;
import com.kristian.socmed.service.AuthService;
import com.kristian.socmed.service.PostService;
import com.kristian.socmed.service.dto.PostRequest;
import com.kristian.socmed.service.dto.PostResponse;
import com.kristian.socmed.service.dto.ReportedPostDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping({"/api/post", "/api/post/"})
@AllArgsConstructor
public class PostController {

  private PostService postService;
  private AuthService authService;

  @GetMapping("/all")
  public ResponseEntity<List<PostResponse>> getAllPosts() {
    List<PostResponse> allPosts = postService.getAllPosts();
    return new ResponseEntity<>(allPosts, HttpStatus.OK);
  }

  @GetMapping("/topic/{topicName}")
  public ResponseEntity<List<PostResponse>> getAllPostsForTopic(@PathVariable String topicName) {
    List<PostResponse> topicPosts = postService.getAllPostsForTopic(topicName);
    return new ResponseEntity<>(topicPosts, HttpStatus.OK);
  }

  @GetMapping("/authAll")
  public ResponseEntity<List<PostResponse>> getAllPostsForFollowingUsers() {
    List<PostResponse> allPosts = postService.getAllPostsForFollowingUsers();
    return new ResponseEntity<>(allPosts, HttpStatus.OK);
  }

  @SuppressWarnings("unchecked")
  @PostMapping("/create")
  public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostRequest postRequest) {
    User currentUser = authService.getCurrentUser();
    PostResponse postResponse = postService.createPost(postRequest, currentUser);
    return new ResponseEntity(postResponse, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
    PostResponse postResponse = postService.getPost(id);
    return new ResponseEntity<>(postResponse, HttpStatus.OK);
  }

  @GetMapping("/user/{username}")
  public ResponseEntity<List<PostResponse>> getAllPostsForUser(@PathVariable String username) {
    List<PostResponse> postResponses = postService.getAllPostsForUser(username);
    return new ResponseEntity<>(postResponses, HttpStatus.OK);
  }

  @PatchMapping("/soft-delete/{id}")
  public ResponseEntity softDeletePost(@PathVariable Long id) {
    postService.softDeletePost(id);
    return new ResponseEntity(HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deletePost(@PathVariable Long id) {
    postService.deletePost(id);
    return new ResponseEntity(HttpStatus.OK);
  }

  @PatchMapping("/update/{id}")
  public ResponseEntity updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
    postService.updatePost(id, postRequest);
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping("/secured/reported")
  public ResponseEntity<Set<ReportedPostDto>> getAllUnsolvedReportedPosts() {
    Set<ReportedPostDto> reportedPosts = postService.getAllUnsolvedReportedPosts();
    return new ResponseEntity<>(reportedPosts, HttpStatus.OK);
  }

  @GetMapping("/secured/reported-solved")
  public ResponseEntity<Set<ReportedPostDto>> getAllSolvedReportedPosts() {
    Set<ReportedPostDto> reportedPosts = postService.getAllSolvedReportedPosts();
    return new ResponseEntity<>(reportedPosts, HttpStatus.OK);
  }

  @ExceptionHandler(MyRuntimeException.class)
  public ResponseEntity<String> handleMyRuntimeException(MyRuntimeException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    List<ObjectError> allErrors = ex.getAllErrors();
    List<String> collect = allErrors.stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
    return new ResponseEntity<>(collect, HttpStatus.BAD_REQUEST);
  }

}
