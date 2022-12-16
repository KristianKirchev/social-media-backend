package com.kristian.socmed.model.entity;

import java.time.Instant;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Topic implements MyEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Topic name is required")
    @Column(unique = true)
    private String name;

    private String description;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;
    
    private Instant date;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
