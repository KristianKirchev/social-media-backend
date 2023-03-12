package com.kristian.socmed;

import static org.testcontainers.images.PullPolicy.alwaysPull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;
import com.kristian.socmed.service.MailService;

@ActiveProfiles({"it"})
@SpringBootTest
class SocmedApplicationIT {

  @Autowired
  private MailService mailService;

  private static final MySQLContainer<?> MYSQL_CONTAINER;

  static {
    DockerImageName image = DockerImageName.parse("mysql:8").asCompatibleSubstituteFor("mysql");
    MYSQL_CONTAINER = new MySQLContainer<>(image).withStartupTimeoutSeconds(600)
        .withFileSystemBind("./platform/mysql/initdb", "/docker-entrypoint-initdb.d", BindMode.READ_ONLY)
        .withImagePullPolicy(alwaysPull())
        .withDatabaseName("kris_test")
        .withUsername("root")
        .withPassword("root");

    MYSQL_CONTAINER.start();
  }

  @DynamicPropertySource
  public static void dynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
  }

  @Test
  void contextLoads() {
    Assertions.assertNotNull(mailService);
  }
}
