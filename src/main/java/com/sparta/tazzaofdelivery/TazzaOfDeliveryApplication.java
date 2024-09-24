package com.sparta.tazzaofdelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication
public class TazzaOfDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TazzaOfDeliveryApplication.class, args);
    }

}
