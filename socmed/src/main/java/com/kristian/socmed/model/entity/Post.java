package com.kristian.socmed.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "Post")
public class Post {
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
	
	@Lob
    @Column(name = "photo")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] photo;
}
