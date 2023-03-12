package com.kristian.socmed.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto implements Dto {
  private Long id;

  @NotBlank(message = "Name is required")
  private String name;

  private String description;
  private Integer numberOfPosts;
}
