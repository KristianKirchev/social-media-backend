package com.kristian.socmed.service.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ocpsoft.prettytime.PrettyTime;

import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.Post;
import com.kristian.socmed.model.entity.Reaction;
import com.kristian.socmed.model.entity.ReactionType;
import com.kristian.socmed.repository.CommentRepository;
import com.kristian.socmed.repository.ReactionRepository;
import com.kristian.socmed.service.AuthService;
import com.kristian.socmed.service.dto.PostResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostMapper implements GenericMapper<PostResponse, Post> {

    private CommentRepository commentRepository;
    private ReactionRepository reactionRepository;
    private AuthService authService;


    @Override
    public Post toEntity(PostResponse dto) {
        return null;
    }

    @Override
    public PostResponse toDto(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setContent(post.getContent());
        postResponse.setTopicName(post.getTopic().getName());
        postResponse.setUserName(post.getUser().getUsername());
        postResponse.setCommentCount(getCommentCount(post));
        postResponse.setDislikes(getDislikesCount(post));
        postResponse.setLikes(getLikesCount(post));
        postResponse.setLiked(isLiked(post.getId()));
        postResponse.setDisliked(isDisliked(post.getId()));
        postResponse.setUsernameLikes(getUsernameLikes(post));
        postResponse.setUsernameDislikes(getUsernameDislikes(post));
        PrettyTime p = new PrettyTime();
        postResponse.setDuration( p.format(post.getDate()));
        return postResponse;
    }

    private List<String> getUsernameDislikes(Post post) {
        List<Reaction> likes = reactionRepository.findByPostAndReactionType(post, ReactionType.DISLIKE);
        return likes.stream().map((like)->like.getUser().getUsername()).collect(Collectors.toList());
    }

    private List<String> getUsernameLikes(Post post) {
        List<Reaction> likes = reactionRepository.findByPostAndReactionType(post, ReactionType.LIKE);
        return likes.stream().map((like)->like.getUser().getUsername()).collect(Collectors.toList());
    }

    private boolean isDisliked(Long id) {
        try{
            Optional<Reaction> optReaction = reactionRepository.findByPost_idAndUser(id, authService.getCurrentUser());
            if(!optReaction.isPresent()){
                return false;
            }
            return optReaction.get().getReactionType()==ReactionType.DISLIKE;
        }catch (MyRuntimeException ex){
            return false;
        }

    }

    private boolean isLiked(Long postId) {
        try{
            Optional<Reaction> optReaction = reactionRepository.findByPost_idAndUser(postId, authService.getCurrentUser());
            if(!optReaction.isPresent()){
                return false;
            }
            return optReaction.get().getReactionType()==ReactionType.LIKE;
        }catch (MyRuntimeException ex){
            return false;
        }

    }

    private Integer getDislikesCount(Post post) {
        return reactionRepository.findByPostAndReactionType(post, ReactionType.DISLIKE).size();
    }

    private Integer getLikesCount(Post post) {
        return reactionRepository.findByPostAndReactionType(post, ReactionType.LIKE).size();
    }

    private Integer getCommentCount(Post post) {
       return commentRepository.findAllByPost(post).size();
    }
}
