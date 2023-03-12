package com.kristian.socmed.model.entity;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements MyEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @NotBlank(message = "Enter username!")
  @Column(unique = true)
  private String username;

  @Email
  @NotBlank(message = "Enter email!")
  @Column(unique = true)
  private String email;

  @NotBlank(message = "Enter password!")
  private String password;

  private String bio;

  private Instant date;

  private boolean isEnabled;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "following", joinColumns = @JoinColumn(name = "following_user_id"),
      inverseJoinColumns = @JoinColumn(name = "followed_user_id"))
  private List<User> following;

  @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY)
  private List<User> followers;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "userId")},
      inverseJoinColumns = {@JoinColumn(name = "roleId")})
  private Set<Role> roles;

  public int getMutualFollowers(User currentUser) {
    List<User> listOfMutualFoll = followers.stream()
        .filter(two -> currentUser.getFollowers().stream().anyMatch(one -> one.getUsername().equals(two.getUsername())))
        .toList();

    return listOfMutualFoll.size();
  }

  public void addRole(Role role) {
    if (roles == null) {
      roles = new LinkedHashSet<>();
    }

    roles.add(role);
  }

  public boolean isEnabled() {
    return isEnabled;
  }
}
