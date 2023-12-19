package com.lullaby.cardstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CardStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardStudyApplication.class, args);
    }

}
