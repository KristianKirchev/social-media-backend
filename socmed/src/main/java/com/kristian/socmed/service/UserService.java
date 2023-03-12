package com.kristian.socmed.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.Following;
import com.kristian.socmed.model.entity.FollowingId;
import com.kristian.socmed.model.entity.PostReport;
import com.kristian.socmed.model.entity.ReportStatus;
import com.kristian.socmed.model.entity.Role;
import com.kristian.socmed.model.entity.User;
import com.kristian.socmed.repository.FollowRepository;
import com.kristian.socmed.repository.PostReportRepository;
import com.kristian.socmed.repository.RoleRepository;
import com.kristian.socmed.repository.UserRepository;
import com.kristian.socmed.service.dto.ReportedUserDto;
import com.kristian.socmed.service.dto.UserDto;
import com.kristian.socmed.service.mapper.ReportedUserMapper;
import com.kristian.socmed.service.mapper.UserMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
  private UserRepository userRepository;
  private FollowRepository followRepository;
  private UserMapper userMapper;
  private AuthService authService;
  private RoleRepository roleRepository;
  private PostReportRepository postReportRepository;
  private ReportedUserMapper reportedUserMapper;

  @Transactional
  public void follow(String username) {
    User currentUser = authService.getCurrentUser();
    Optional<User> userOptFollowed = userRepository.findByUsername(username);

    User userFollowed = userOptFollowed.orElseThrow(() -> new MyRuntimeException("User not found"));

    if (userFollowed.getUsername().equals(currentUser.getUsername())) {
      throw new MyRuntimeException("Following not allowed");
    }

    Following following = new Following(currentUser, userFollowed, Instant.now());
    followRepository.save(following);
  }

  @Transactional
  public void unfollow(User currentUser, String username) {
    Optional<User> userOptFollowed = userRepository.findByUsername(username);

    User userFollowed = userOptFollowed.orElseThrow(() -> new MyRuntimeException("User not found"));

    if (userFollowed.getUsername().equals(currentUser.getUsername())) {
      throw new MyRuntimeException("Following not allowed");
    }

    Following following = new Following(currentUser, userFollowed, Instant.now());
    followRepository.delete(following);
  }

  @Transactional
  public UserDto getUser(Long id) {
    Optional<User> userOpt = userRepository.findById(id);
    User user = userOpt.orElseThrow(() -> new MyRuntimeException("User with id : " + id + " not found"));
    return userMapper.toDto(user);
  }

  @Transactional
  public void unfollow(Long idFollowing, Long idFollowed) {
    Optional<User> userOptFollowing = userRepository.findById(idFollowing);
    Optional<User> userOptFollowed = userRepository.findById(idFollowed);

    User userFollowing = userOptFollowing.orElseThrow(() -> new MyRuntimeException("User not found"));
    User userFollowed = userOptFollowed.orElseThrow(() -> new MyRuntimeException("User not found"));

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!userFollowing.getUsername().equals(username) || userFollowed.getUsername().equals(username)) {
      throw new MyRuntimeException("Unfollowing not allowed");
    }
    Optional<Following> follOpt = followRepository.findById(new FollowingId(idFollowing, idFollowed));
    Following foll = follOpt.orElseThrow(() -> new MyRuntimeException("You dont follow this user"));
    followRepository.delete(foll);
  }

  @Transactional
  public List<UserDto> getAllFollowersForUser(String username) {
    Optional<User> userOpt = userRepository.findByUsername(username);
    User user = userOpt.orElseThrow(() -> new MyRuntimeException("User not found"));
    List<User> followers = user.getFollowers();
    return followers.stream().map((user1) -> userMapper.toDto(user1)).collect(Collectors.toList());
  }

  @Transactional
  public List<UserDto> getAllFollowingForUser(String username) {
    Optional<User> userOpt = userRepository.findByUsername(username);
    User user = userOpt.orElseThrow(() -> new MyRuntimeException("User not found"));
    List<User> followers = user.getFollowing();
    return followers.stream().map((user1) -> userMapper.toDto(user1)).collect(Collectors.toList());
  }

  @Transactional
  public UserDto getProfileInfo(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new MyRuntimeException("User not found"));
    return userMapper.toDto(user);
  }

  @Transactional
  public List<UserDto> getAllSuggestedUsers() {
    List<User> notFollowing = userRepository.findByUserIdNotInAndIsEnabled(authService.getCurrentUser()
        .getFollowing()
        .stream()
        .map((user) -> user.getUserId())
        .collect(Collectors.toList()), true);
    if (notFollowing.size() == 0) {
      List<Long> myId = new ArrayList<>();
      myId.add(authService.getCurrentUser().getUserId());
      notFollowing = userRepository.findByUserIdNotIn(myId);
    }
    notFollowing.remove(authService.getCurrentUser());
    Collections.sort(notFollowing, (user1, user2) -> user2.getMutualFollowers(authService.getCurrentUser())
        - user1.getMutualFollowers(authService.getCurrentUser()));
    return notFollowing.stream().map((user) -> userMapper.toDto(user)).collect(Collectors.toList());
  }

  @Transactional
  public void updateUser(UserDto userDto) {
    User user =
        userRepository.findById(userDto.getUserId()).orElseThrow(() -> new MyRuntimeException("User not found"));
    if (userRepository.findByUsername(userDto.getUsername()).isPresent()
        && !user.getUsername().equals(userDto.getUsername())) {
      throw new MyRuntimeException("Username taken");
    }
    if (userRepository.findByEmail(userDto.getEmail()).isPresent() && !user.getEmail().equals(userDto.getEmail())) {
      throw new MyRuntimeException("Email linked to another account");
    }
    user.setBio(userDto.getBio());
    user.setUsername(userDto.getUsername());
    user.setEmail(userDto.getEmail());
    userRepository.save(user);
  }

  public void assignRole(String username, String rolename) {
    Role role = roleRepository.findByName(rolename).orElseThrow(() -> new MyRuntimeException(("Role not found")));
    User user = userRepository.findByUsername(username).orElseThrow(() -> new MyRuntimeException("User not found"));
    user.addRole(role);
    userRepository.save(user);
  }

  @Transactional
  public List<ReportedUserDto> getReportedUsers() {
    List<PostReport> postReports = postReportRepository.findByReportStatus(ReportStatus.DELETED);
    List<User> users = postReports.stream().map(report -> report.getPost().getUser()).collect(Collectors.toList());
    return users.stream().distinct().map(user -> reportedUserMapper.toDto(user)).collect(Collectors.toList());
  }

  public void disableUser(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new MyRuntimeException("User not found"));
    user.setEnabled(false);
    userRepository.save(user);
  }

  public void enableUser(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new MyRuntimeException("User not found"));
    user.setEnabled(true);
    userRepository.save(user);
  }
}
