package com.kristian.socmed.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kristian.socmed.model.entity.Comment;
import com.kristian.socmed.model.entity.Notification;
import com.kristian.socmed.repository.CommentRepository;
import com.kristian.socmed.service.dto.CommentDto;
import com.kristian.socmed.service.mapper.CommentMapper;
import com.kristian.socmed.service.mapper.NotificationBuilder;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
	private CommentRepository commentRepository;
    private CommentMapper commentMapper;
    private NotificationService notificationService;
    private NotificationBuilder notificationBuilder;
    
    @Transactional
    public void comment(CommentDto commentDto){
        Comment comment = commentMapper.toEntity(commentDto);
        commentRepository.save(comment);
        Notification notification = notificationBuilder.createNotificationForComment(comment);
        notificationService.save(notification);
    }
    
    @Transactional
    public List<CommentDto> getAllCommentsForPost(Long postId) {
        List<Comment> comments = commentRepository.findByPost_idOrderByDateDesc(postId);
        return comments.stream().map((comment)->commentMapper.toDto(comment)).collect(Collectors.toList());
    }
    
    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
