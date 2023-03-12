package com.kristian.socmed.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReportRequest implements Dto {
  private Long postId;
  private String username;
}
