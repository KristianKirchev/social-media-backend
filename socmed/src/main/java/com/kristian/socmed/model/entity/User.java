package com.kristian.socmed.model.entity;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    @Column(name = "Username")
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
}
