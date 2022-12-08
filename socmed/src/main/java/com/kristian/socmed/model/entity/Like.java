package com.kristian.socmed.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "Like")
public class Like {
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;
}
