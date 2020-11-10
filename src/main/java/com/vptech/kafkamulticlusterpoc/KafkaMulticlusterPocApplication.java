package com.vptech.kafkamulticlusterpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaMulticlusterPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaMulticlusterPocApplication.class, args);
    }
}
