package com.kristian.socmed.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.ReportStatus;
import com.kristian.socmed.service.PostReportService;
import com.kristian.socmed.service.dto.PostReportRequest;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/report")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class PostReportController {
    private PostReportService service;

    @PostMapping
    public ResponseEntity reportPost(@RequestBody PostReportRequest reportRequest){
        service.reportPost(reportRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

	@PatchMapping("/change-status/{postId}")
    public ResponseEntity changeReportStatus(@RequestBody ReportStatus reportStatus,@PathVariable Long postId){
        service.changeReportStatus(reportStatus,postId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(MyRuntimeException.class)
    public  ResponseEntity<String> handleMyRuntimeException(MyRuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public  ResponseEntity<String> handleSQLIntegrityConstraintViolation(SQLIntegrityConstraintViolationException ex){
        return new ResponseEntity<>("You have already reported this post",HttpStatus.BAD_REQUEST);
    }

}