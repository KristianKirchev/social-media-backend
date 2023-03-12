package com.kristian.socmed.service.mapper;

import com.kristian.socmed.model.entity.PostReport;
import com.kristian.socmed.model.entity.ReportStatus;
import com.kristian.socmed.repository.PostRepository;
import com.kristian.socmed.service.AuthService;
import com.kristian.socmed.service.dto.PostReportRequest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PostReportRequestMapper implements GenericMapper<PostReportRequest, PostReport> {

  private PostRepository postRepository;
  private AuthService authService;

  @SuppressWarnings("deprecation")
  @Override
  public PostReport toEntity(PostReportRequest dto) {
    PostReport postReport = new PostReport();
    postReport.setPost(postRepository.getById(dto.getPostId()));
    postReport.setUser(authService.getCurrentUser());
    postReport.setReportStatus(ReportStatus.UNSOLVED);
    return postReport;
  }

  @Override
  public PostReportRequest toDto(PostReport entity) {
    return null;
  }
}
