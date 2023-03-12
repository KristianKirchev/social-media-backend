package com.kristian.socmed.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest implements Dto {
  @NotBlank(message = "Title is required")
  private String title;

  @NotBlank(message = "Topic is required")
  private String topicName;

  @NotBlank(message = "Content is required")
  private String content;
}
