package com.kristian.socmed.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@Builder
@Entity
@Table(name = "Post")
public class Post {
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
	
	@Lob
	@NotNull
    @Column(name = "photo")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] photo;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
	
	@OneToMany(mappedBy = "post")
	@Singular
    private Set<Like> likes = new HashSet<>();
	
	@OneToMany(mappedBy = "post")
	@Singular
    private Set<Comment> comments = new HashSet<>();
}
