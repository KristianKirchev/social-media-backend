package com.kristian.socmed.model.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.context.ApplicationContext;

import com.kristian.socmed.repository.MyRepository;

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
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
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
    
    @Override
    public List<MyRepository> returnChildRepositories(ApplicationContext context) {
        MyRepository commentRepository = (MyRepository) context.getBean("commentRepository");
        MyRepository notificationRepository = (MyRepository) context.getBean("notificationRepository");
        MyRepository reactionRepository = (MyRepository) context.getBean("reactionRepository");
        MyRepository postReportRepository = (MyRepository) context.getBean("postReportRepository");
        List<MyRepository> list = new ArrayList<MyRepository>();
        list.add(commentRepository);
        list.add(notificationRepository);
        list.add(reactionRepository);
        list.add(postReportRepository);
        
        return list;
    }	
}
