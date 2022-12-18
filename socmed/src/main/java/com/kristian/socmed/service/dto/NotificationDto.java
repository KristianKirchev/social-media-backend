package com.kristian.socmed.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto implements Dto{
    private Long id;
    private Long postId;
    private String message;
    private boolean read;
}
