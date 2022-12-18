package com.kristian.socmed.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InboxMessageDto implements Dto{
    String with;
    String content;
    String time;
    int newMessages;
}
