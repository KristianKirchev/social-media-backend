package com.kristian.socmed.controller;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.service.MessageService;
import com.kristian.socmed.service.dto.InboxMessageDto;
import com.kristian.socmed.service.dto.MessageDto;
import lombok.AllArgsConstructor;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping({"/api/message", "/api/message/"})
@AllArgsConstructor
public class MessageController {

  private MessageService messageService;

  @PostMapping("/{id}")
  public ResponseEntity sendMessage(@PathVariable String id, @RequestBody @Valid MessageDto messageDto) {
    messageService.saveMessage(messageDto, id);
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping("/new-msg-count")
  public ResponseEntity<Integer> getNewMsgCount() {
    Integer newMsgCount = messageService.getNewMsgCount();
    return new ResponseEntity<>(newMsgCount, HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<MessageDto>> getAllMessages() {
    List<MessageDto> allMessages = messageService.getAllMessages();
    return new ResponseEntity<>(allMessages, HttpStatus.OK);
  }

  @PatchMapping("/read/{username}")
  public ResponseEntity readMessagesFrom(@PathVariable String username) {
    messageService.readMessagesFrom(username);
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping("/last/{from}/{to}")
  public ResponseEntity<MessageDto> getLastMessage(@PathVariable String from, @PathVariable String to) {
    MessageDto dto = messageService.getLastMesage(from, to);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @GetMapping("/all/{from}/{to}")
  public ResponseEntity<List<MessageDto>> getAllFromChat(@PathVariable String from, @PathVariable String to) {
    List<MessageDto> allFromChat = messageService.getAllFromChat(from, to);
    return new ResponseEntity<>(allFromChat, HttpStatus.OK);
  }

  @GetMapping("/inbox")
  public ResponseEntity<List<InboxMessageDto>> inboxMessages() {
    List<InboxMessageDto> inboxMessages = messageService.inboxMessages();
    return new ResponseEntity<>(inboxMessages, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteMessage(@PathVariable Long id) {
    messageService.delete(id);
  }

  @ExceptionHandler(MyRuntimeException.class)
  public ResponseEntity handleMyRuntimeEx() {

    return new ResponseEntity(HttpStatus.OK);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    List<ObjectError> allErrors = ex.getAllErrors();
    List<String> collect = allErrors.stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
    return new ResponseEntity<>(collect, HttpStatus.BAD_REQUEST);
  }
}
