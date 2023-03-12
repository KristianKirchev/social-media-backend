package com.kristian.socmed.model.entity;

import java.time.Instant;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Topic implements MyEntity {
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
