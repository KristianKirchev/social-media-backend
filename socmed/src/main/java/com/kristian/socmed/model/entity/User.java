package com.kristian.socmed.model.entity;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
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
    private Set<Post> posts = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
    private Set<Like> likes = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();
}
