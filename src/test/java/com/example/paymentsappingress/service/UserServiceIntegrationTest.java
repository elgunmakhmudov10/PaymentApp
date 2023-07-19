package com.example.paymentsappingress.service;

import com.example.paymentsappingress.dto.request.UserRequest;
import com.example.paymentsappingress.dto.response.UserResponse;
import com.example.paymentsappingress.model.User;
import com.example.paymentsappingress.repository.UserRepository;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.PostgreSQLContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;

import java.util.List;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
// @ActiveProfiles("redis")

public class UserServiceIntegrationTest {
    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    //  @Autowired
    // private RedisCacheManager cacheManager;


    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("Payment-DB")
            .withPassword("password")
            .withUsername("postgres");
    /**
     * redis container test
     */
//    @Container
//    public static GenericContainer redis = new GenericContainer("redis:latest");


    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
    }

    /**
     * Redis check
     */
//    @DynamicPropertySource
//    public static void redisProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.redis.host", redis::getHost);
//        registry.add("spring.redis.port", redis::getFirstMappedPort);
//    }

    /**
     * redis start in comment
     */
    @BeforeAll
    public static void setUp() {
        postgreSQLContainer.start();
//        redis.start();
    }

    /**
     * Redis cache manager test
     */
//    @Test
//    void testFindAllComeFromCache() {
//        //Arrange
//        Cache cache = cacheManager.getCache("users");
//
//        //Act
//        List<UserResponse> users = userService.findAll();
//
//        //Assert
//        Cache.ValueWrapper cacheResult = cache.get("findAll");
//        assertThat(users).isEqualTo(cacheResult.get());
//    }

    @Test
    void testGetAccountById() {
        User user = userRepository.save(User.builder()
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build());

        UserResponse userResponse = UserResponse.builder()
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        modelMapper.map(user, userResponse);

        //Act
        UserResponse byId = userService.findById(user.getId());

        //Assert
        assertThat(byId).isEqualTo(userResponse);
    }

    @Test
    void testCreateAccount() {
        //Arrange
        UserRequest userRequestDto = UserRequest.builder()
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        //Act
        var save = userService.save(userRequestDto);

        //Assert
        assertThat(save.getId()).isNotNull();
        assertThat(save.getName()).isEqualTo("Elgun");
        assertThat(save.getUserName()).isEqualTo("Mahmudov");

        List<User> all = userRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getName()).isEqualTo("Elgun");
        assertThat(all.get(0).getUserName()).isEqualTo("Mahmudov");
        assertThat(all.get(0).getId()).isEqualTo(save.getId());
    }


    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
     //   redis.stop();
    }
    /**
     * redis stop
     */
}


