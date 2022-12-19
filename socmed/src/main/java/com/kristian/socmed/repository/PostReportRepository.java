package com.kristian.socmed.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kristian.socmed.model.entity.MyEntity;
import com.kristian.socmed.model.entity.Post;
import com.kristian.socmed.model.entity.PostReport;
import com.kristian.socmed.model.entity.ReportStatus;
import com.kristian.socmed.model.entity.User;

public interface PostReportRepository extends JpaRepository<PostReport,Long>, MyRepository {
	
	@Override
    default void deleteByParent(MyEntity parent) {
        deleteAllByPost((Post) parent);
    }

    public void deleteAllByPost(Post post);
    Long countByPost(Post post);
    Optional<PostReport> findFirstByPost(Post post);
    List<PostReport> findAllByReportStatus(ReportStatus unsolved);
    List<PostReport> findAllByReportStatusIn(List<ReportStatus> statuses);
    List<PostReport> findByPost_id(Long postId);
    List<PostReport> findByReportStatus(ReportStatus status);
    List<PostReport> findByPost_userAndReportStatus(User user, ReportStatus status);
}
