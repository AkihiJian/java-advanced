package com.icycoke.springsecuritydemo;

import com.icycoke.SpringSecurityDemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest(classes = SpringSecurityDemoApplication.class)
class SpringSecurityDemoApplicationTests {

    @Test
    void contextLoads() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("jian");
        System.out.println(result);
        String result2 = encoder.encode("jianjian");
        System.out.println(result2);

        boolean b = encoder.matches("jian", result);
        System.out.println(b);

    }

}
