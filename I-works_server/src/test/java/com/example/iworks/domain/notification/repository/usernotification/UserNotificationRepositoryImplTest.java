package com.example.iworks.domain.notification.repository.usernotification;

import com.example.iworks.InitData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:env.properties")
class UserNotificationRepositoryImplTest {

    @Value("${MYSQL_USER}")
    private String mysqlUser;

    @Value("${MYSQL_PASSWORD}")
    private String mysqlPassword;

    @Value("${REDIS_HOST:localhost}")
    private String redisHost;

    @Value("${REDIS_PORT:6379}")
    private String redisPort;

    @Value("${JWT_SECRET_KEY:your_default_secret}")
    private String jwtSecret;

//    @Autowired
//    UserNotificationRepository userNotificationRepository;

//    @Autowired
//    InitData initData;

    @BeforeEach
    void setUp(){
        System.out.println("set up");
//        initData.init();
    }

    @Test
    void findByUserEid() {


    }
}