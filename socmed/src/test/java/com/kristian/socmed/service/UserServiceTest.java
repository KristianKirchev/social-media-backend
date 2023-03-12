package com.kristian.socmed.service;

import static com.kristian.socmed.utils.SocMedTestUtils.USER_NAME_1;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.kristian.socmed.model.entity.User;
import com.kristian.socmed.repository.FollowRepository;
import com.kristian.socmed.repository.PostReportRepository;
import com.kristian.socmed.repository.RoleRepository;
import com.kristian.socmed.repository.UserRepository;
import com.kristian.socmed.service.dto.UserDto;
import com.kristian.socmed.service.mapper.ReportedUserMapper;
import com.kristian.socmed.service.mapper.UserMapper;
import com.kristian.socmed.utils.SocMedTestUtils;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private FollowRepository followRepository;

  @Mock
  private UserMapper userMapper;

  @Mock
  private AuthService authService;

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private PostReportRepository postReportRepository;

  @Mock
  private ReportedUserMapper reportedUserMapper;

  @InjectMocks
  private UserService userService;

  @Test
  void shouldGetAllFollowingForUser() {
    User user1 = SocMedTestUtils.getUser1();
    Mockito.when(userRepository.findByUsername(USER_NAME_1)).thenReturn(Optional.of(user1));

    List<UserDto> allFollowingForUser = userService.getAllFollowingForUser(USER_NAME_1);

    Assertions.assertNotNull(allFollowingForUser);
    Assertions.assertEquals(2, allFollowingForUser.size());
  }
}
