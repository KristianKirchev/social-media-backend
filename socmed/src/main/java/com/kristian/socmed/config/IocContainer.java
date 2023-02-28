package com.kristian.socmed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kristian.socmed.repository.CommentRepository;
import com.kristian.socmed.repository.PostReportRepository;
import com.kristian.socmed.repository.PostRepository;
import com.kristian.socmed.repository.ReactionRepository;
import com.kristian.socmed.repository.TopicRepository;
import com.kristian.socmed.repository.UserRepository;
import com.kristian.socmed.service.AuthService;
import com.kristian.socmed.service.mapper.CommentMapper;
import com.kristian.socmed.service.mapper.MessageMapper;
import com.kristian.socmed.service.mapper.NotificationBuilder;
import com.kristian.socmed.service.mapper.NotificationMapper;
import com.kristian.socmed.service.mapper.PostMapper;
import com.kristian.socmed.service.mapper.PostReportRequestMapper;
import com.kristian.socmed.service.mapper.PostRequestMapper;
import com.kristian.socmed.service.mapper.ReactionMapper;
import com.kristian.socmed.service.mapper.ReportedPostMapper;
import com.kristian.socmed.service.mapper.ReportedUserMapper;
import com.kristian.socmed.service.mapper.TopicMapper;
import com.kristian.socmed.service.mapper.UserMapper;
import com.kristian.socmed.websockettest.UserHandshakeHandler;

@Configuration
public class IocContainer {
    @Bean
    NotificationBuilder notificationBuilder(AuthService authService){
        return new NotificationBuilder();
    }

    @Bean
    NotificationMapper notificationMapper(){
        return new NotificationMapper();
    }
    
    @Bean
    UserMapper userMapper(AuthService authService){
        return new UserMapper(authService);
    }

    @Bean
    PostMapper postMapper(CommentRepository commentRepository, ReactionRepository reactionRepository, AuthService authService){
        return new PostMapper(commentRepository,reactionRepository,authService);
    }

    @Bean
    TopicMapper topicMapper(PostRepository postRepository, AuthService authService){
        return new TopicMapper(postRepository,authService);
    }

    @Bean
    PostRequestMapper PostRequestMapper(TopicRepository topicRepository){
        return new PostRequestMapper(topicRepository);
    }

    @Bean
    CommentMapper commentMapper(PostRepository postRepository,AuthService authService){
        return new CommentMapper(postRepository,authService);
    }

    @Bean
    ReactionMapper reactionMapper(PostRepository postRepository,AuthService authService){
        return new ReactionMapper(postRepository,authService);
    }

    @Bean
    PostReportRequestMapper postReportRequestMapper(PostRepository postRepository,AuthService authService){
        return new PostReportRequestMapper(postRepository,authService);
    }

    @Bean
    ReportedPostMapper reportedPostMapper(PostReportRepository reportRepository){
        return new ReportedPostMapper(reportRepository);
    }

    @Bean
    ReportedUserMapper reportedUserMapper(PostReportRepository postReportRepository){
        return new ReportedUserMapper(postReportRepository);
    }

    @Bean
    MessageMapper messageMapper(UserRepository userRepository){
        return new MessageMapper(userRepository);
    }
    
    @Bean
    UserHandshakeHandler userHandshakeHandler(){
        return new UserHandshakeHandler();
    }
}