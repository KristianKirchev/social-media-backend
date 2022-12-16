package com.kristian.socmed.model.entity;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements MyEntity {
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;
	
	@NotNull(message = "Enter username!")
	@NotBlank(message = "Enter username!")
    @Column(unique = true)
    private String username;
	
	@Email
	@NotNull(message = "Enter email!")
	@NotBlank(message = "Enter email!")
    @Column(unique = true)
    private String email;
	
	@NotNull(message = "Enter password!")
	@NotBlank(message = "Enter password!")
    private String password;
	
	private String bio;
	
	@Lob
    @Column(name = "profile_picture")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] profilePicture;
	
	private Instant dateOfCreation;
	
    private boolean isEnabled;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "following",
            joinColumns = @JoinColumn(name = "following_user_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_user_id"))
    private List<User> following;

    @ManyToMany(mappedBy = "following",fetch = FetchType.LAZY)
    private List<User> followers;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles"
            ,joinColumns = {@JoinColumn(name = "userId")}
            ,inverseJoinColumns = {@JoinColumn(name="roleId")})
    private Set<Role> roles;
    
    public int getMutualFollowers(User currentUser) {
        List<User> listOfMutualFoll = followers.stream()
                .filter(two -> currentUser
                		.getFollowers().
                		stream().
                		anyMatch(one -> one.getUsername()
                				.equals(two.getUsername())))
                .collect(Collectors.toList());
        return listOfMutualFoll.size();
    }

    public void addRole(Role role){
        if(roles==null){
            roles = new LinkedHashSet<>();
        }
        roles.add(role);
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
