package com.kristian.socmed.model.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Data
@Getter
@Setter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User {
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
	
	@NotNull(message = "Enter username!")
	@NotBlank(message = "Enter username!")
    @Column(name = "username")
    private String username;
	
	@Email
	@NotNull(message = "Enter email!")
	@NotBlank(message = "Enter email!")
    @Column(name = "email", unique = true)
    private String email;
	
	@NotNull(message = "Enter password!")
	@NotBlank(message = "Enter password!")
    @Column(name = "password")
    private String password;
	
	private String bio;
	
	@Lob
    @Column(name = "profile_picture")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] profilePicture;
	
	private Instant dateOfCreation;
    private boolean isEnabled;
	
	@OneToMany(mappedBy = "user")
	@Singular
    private Set<Post> posts = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	@Singular
    private Set<Reaction> likes = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	@Singular
    private Set<Comment> comments = new HashSet<>();
}
