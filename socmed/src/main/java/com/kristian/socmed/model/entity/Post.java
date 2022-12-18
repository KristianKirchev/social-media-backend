package com.kristian.socmed.model.entity;

import java.time.Instant;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post implements MyEntity {
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank(message = "Post must have title")
    private String title;
	
//	@Lob
//	@NotNull
//    @Column(name = "photo")
//    @Type(type = "org.hibernate.type.BinaryType")
//    private byte[] photo;
	
	@Column(columnDefinition="text")
    private String content;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topicId", referencedColumnName = "id")
    private Topic topic;
	
	private Instant date;
	
    private Instant deletebByAdmin;
}
