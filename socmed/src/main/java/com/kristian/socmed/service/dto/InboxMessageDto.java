package com.kristian.socmed.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InboxMessageDto implements Dto {
  private String with;
  private String content;
  private String time;
  private int newMessages;
}
