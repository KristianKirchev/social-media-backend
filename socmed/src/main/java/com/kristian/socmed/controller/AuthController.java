package com.kristian.socmed.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kristian.socmed.service.AuthService;
import com.kristian.socmed.service.AuthTransactionService;
import com.kristian.socmed.service.dto.AuthResponse;
import com.kristian.socmed.service.dto.LoginRequest;
import com.kristian.socmed.service.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping({"/api/auth", "/api/auth/"})
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class AuthController {
  private AuthService authService;
  private AuthTransactionService authTransactionService;

  @SuppressWarnings("unchecked")
  @GetMapping("/activate/{token}")
  public ResponseEntity<String> activateAccount(@PathVariable String token) {
    authTransactionService.activateAccount(token);
    return new ResponseEntity("Acount succesfuly activated, you can close this page now", HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody @Valid RegisterRequest registerRequest) {
    authService.signup(registerRequest);
    return new ResponseEntity<>("Registration succesfull", HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public AuthResponse login(@RequestBody @Valid LoginRequest loginRequest) {
    return authService.login(loginRequest);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<String> handleDataIntegrityViolationException() {
    return new ResponseEntity<>("Account with given username or email already exists", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    List<ObjectError> allErrors = ex.getAllErrors();
    List<String> collect = allErrors.stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
    return new ResponseEntity<>(collect, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<String> authExceptionHandler(AuthenticationException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
  }
}
