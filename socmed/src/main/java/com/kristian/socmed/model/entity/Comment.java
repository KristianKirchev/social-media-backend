package com.kristian.socmed.model.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "Comment")
public class Comment {
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
	
	@NotNull
	@Column(name = "content")
	private String content;
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	@Column(name = "content_date")
	private Date date;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;
}
