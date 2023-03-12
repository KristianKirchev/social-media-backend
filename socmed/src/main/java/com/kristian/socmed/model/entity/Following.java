package com.kristian.socmed.model.entity;

import java.time.Instant;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(FollowingId.class)
public class Following implements MyEntity {
  @Id
  @ManyToOne(optional = false)
  @JoinColumn(name = "following_user_id", nullable = false)
  private User following;

  @Id
  @ManyToOne(optional = false)
  @JoinColumn(name = "followed_user_id", nullable = false)
  private User followed;

  private Instant date;
}
