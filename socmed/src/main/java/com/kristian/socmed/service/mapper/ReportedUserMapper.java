package com.kristian.socmed.service.mapper;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import com.kristian.socmed.model.entity.PostReport;
import com.kristian.socmed.model.entity.ReportStatus;
import com.kristian.socmed.model.entity.User;
import com.kristian.socmed.repository.PostReportRepository;
import com.kristian.socmed.service.dto.ReportedUserDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReportedUserMapper implements GenericMapper<ReportedUserDto, User> {

  private PostReportRepository postReportRepository;

  @Override
  public User toEntity(ReportedUserDto dto) {
    return null;
  }

  @Override
  public ReportedUserDto toDto(User user) {
    ReportedUserDto reportedUser = new ReportedUserDto();
    reportedUser.setUsername(user.getUsername());
    reportedUser.setNumberOfViolations(countViolations(user));
    reportedUser.setEnabled(user.isEnabled());
    return reportedUser;
  }

  private int countViolations(User user) {
    List<PostReport> reportedPostsOfUser =
        postReportRepository.findByPost_userAndReportStatus(user, ReportStatus.DELETED);
    List<PostReport> distinctByPost =
        reportedPostsOfUser.stream().filter(distinctByPost(PostReport::getPost)).toList();
    return distinctByPost.size();
  }

  public <T> Predicate<T> distinctByPost(Function<? super T, ?> postExtractor) {
    Set<Object> seen = ConcurrentHashMap.newKeySet();
    return t -> seen.add(postExtractor.apply(t));
  }
}
