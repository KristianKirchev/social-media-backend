package com.kristian.socmed.utils;

import java.util.List;
import com.kristian.socmed.model.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SocMedTestUtils {
  public static final String ADMIN_USER_NAME = "admin";
  public static final Long ADMIN_USER_ID = 1l;

  public static final String USER_NAME_1 = "test-user-1";
  public static final Long USER_ID_1 = 2l;

  public static final String USER_NAME_2 = "test-user-2";
  public static final Long USER_ID_2 = 3l;

  public static User getAdminUser() {
    User user = new User();

    user.setUserId(ADMIN_USER_ID);
    user.setUsername(ADMIN_USER_NAME);

    return user;
  }

  public static User getUser1() {
    User user = new User();

    user.setUserId(USER_ID_1);
    user.setUsername(USER_NAME_1);

    user.setFollowing(List.of(getUser2(), getAdminUser()));

    return user;
  }

  public static User getUser2() {
    User user = new User();

    user.setUserId(USER_ID_2);
    user.setUsername(USER_NAME_2);

    return user;
  }

}
