package com.kristian.socmed.model.entity;

import org.hibernate.annotations.Type;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@Entity
@Table(name = "User")
public class User {
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
	
	@NotNull
    @Column(name = "username")
    private String username;
	
	@NotNull
    @Column(name = "email", unique = true)
    private String email;
	
	@NotNull
    @Column(name = "password")
    private String password;
	
	@Lob
    @Column(name = "profile_picture")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] profilePicture;
	
	@OneToMany(mappedBy = "user")
	@Singular
    private Set<Post> posts = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	@Singular
    private Set<Like> likes = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	@Singular
    private Set<Comment> comments = new HashSet<>();
}
