package com.kristian.socmed.service.dto;

import com.kristian.socmed.model.entity.ReportStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedPostDto implements Dto{
    private Long id;
    private String title;
    private String content;
    private String username;
    private String topicname;
    private String duraton;
    private int reportCount;
    private ReportStatus reportStatus;
}
