package com.paklog.value.added.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Value Added Services
 *
 * Kitting, assembly and custom packaging operations
 *
 * @author Paklog Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableKafka
@EnableMongoAuditing
public class ValueAddedServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValueAddedServicesApplication.class, args);
    }
}