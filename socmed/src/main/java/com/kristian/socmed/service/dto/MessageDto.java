package com.kristian.socmed.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Dto {
  private String fromUser;
  private String toUser;
  private String time;
  private boolean seen;

  @NotBlank(message = "Content is required")
  private String content;

}
