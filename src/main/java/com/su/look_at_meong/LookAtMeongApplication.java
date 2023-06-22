package com.su.look_at_meong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LookAtMeongApplication {

    public static void main(String[] args) {
        SpringApplication.run(LookAtMeongApplication.class, args);
    }

}
