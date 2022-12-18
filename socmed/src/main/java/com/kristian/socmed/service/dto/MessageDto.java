package com.kristian.socmed.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Dto{
    String fromUser;
    String toUser;
    @NotEmpty(message = "Content is required")
    @NotNull(message = "Content is required")
    @NotBlank(message = "Content is required")
    String content;
    
    String time;
    boolean seen;
}
