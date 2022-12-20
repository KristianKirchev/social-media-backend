package com.kristian.socmed.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.Post;
import com.kristian.socmed.model.entity.PostReport;
import com.kristian.socmed.model.entity.ReportStatus;
import com.kristian.socmed.repository.PostReportRepository;
import com.kristian.socmed.repository.PostRepository;
import com.kristian.socmed.service.dto.PostReportRequest;
import com.kristian.socmed.service.mapper.PostReportRequestMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostReportService {

    private PostReportRepository postReportRepository;
    private PostRepository postRepository;
    PostReportRequestMapper mapper;
    @Transactional
    public void reportPost(PostReportRequest request){
        PostReport postReport = mapper.toEntity(request);
        List<PostReport> postReportsByPost = postReportRepository.findByPost_id(request.getPostId());
        if(postReportsByPost.stream().anyMatch(report->
                (report.getReportStatus().equals(ReportStatus.APPROVED)
                        ||report.getReportStatus().equals(ReportStatus.DELETED)))){
            throw new MyRuntimeException("This post is already reviewed and approved");
        }
        postReportRepository.save(postReport);
    }
    @Transactional
    public void changeReportStatus(ReportStatus reportStatus,Long postId) {
        List<PostReport> reports = postReportRepository.findByPost_id(postId);
        reports.forEach(r->{
            if(r.getReportStatus().equals(reportStatus)){
                throw new MyRuntimeException("Already has status: "+reportStatus);
            }
            if(reportStatus.equals(ReportStatus.APPROVED)){
                Post post = postRepository.findById(postId).orElseThrow((() -> new MyRuntimeException("Post not found")));
                post.setDeletebByAdmin(null);
                postRepository.save(post);
            }
            r.setReportStatus(reportStatus);
        });
        postReportRepository.saveAll(reports);
    }
}