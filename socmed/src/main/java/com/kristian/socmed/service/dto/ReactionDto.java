package com.kristian.socmed.service.dto;

import com.kristian.socmed.model.entity.ReactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDto implements Dto{
    private Long postId;
    private ReactionType reactionType;
}
