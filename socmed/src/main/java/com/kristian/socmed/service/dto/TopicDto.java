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
public class TopicDto implements Dto{
    private Long id;
    @NotEmpty(message = "Name is required")
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;
    
    private String description;
    private Integer numberOfPosts;
}
