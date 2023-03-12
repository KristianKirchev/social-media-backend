package com.kristian.socmed.service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse implements Dto {
  private Long id;
  private String title;
  private String content;
  private String userName;
  private String topicName;
  private Integer likes;
  private Integer dislikes;
  private Integer commentCount;
  private String duration;
  private boolean liked;
  private boolean disliked;
  private List<String> usernameLikes;
  private List<String> usernameDislikes;
}
