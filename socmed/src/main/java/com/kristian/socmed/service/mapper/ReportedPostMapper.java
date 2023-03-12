package com.kristian.socmed.service.mapper;

import java.util.Optional;

import org.ocpsoft.prettytime.PrettyTime;

import com.kristian.socmed.model.entity.Post;
import com.kristian.socmed.model.entity.PostReport;
import com.kristian.socmed.model.entity.ReportStatus;
import com.kristian.socmed.repository.PostReportRepository;
import com.kristian.socmed.service.dto.ReportedPostDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedPostMapper implements GenericMapper<ReportedPostDto, Post> {

  private PostReportRepository reportRepository;

  @Override
  public Post toEntity(ReportedPostDto dto) {
    return null;
  }

  @Override
  public ReportedPostDto toDto(Post entity) {
    ReportedPostDto dto = new ReportedPostDto();
    dto.setId(entity.getId());
    dto.setContent(entity.getContent());
    dto.setTitle(entity.getTitle());
    PrettyTime p = new PrettyTime();
    dto.setDuraton(p.format(entity.getDate()));
    dto.setTopicname(entity.getTopic().getName());
    dto.setUsername(entity.getUser().getUsername());
    dto.setReportCount(getReportCount(entity));
    dto.setReportStatus(setReportStatus(entity));
    return dto;
  }

  private ReportStatus setReportStatus(Post post) {
    Optional<PostReport> report = reportRepository.findFirstByPost(post);
    return report.get().getReportStatus();
  }

  private int getReportCount(Post post) {
    return reportRepository.countByPost(post).intValue();
  }
}
