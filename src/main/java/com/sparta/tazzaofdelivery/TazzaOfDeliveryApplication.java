package com.sparta.tazzaofdelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TazzaOfDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TazzaOfDeliveryApplication.class, args);
    }

}
