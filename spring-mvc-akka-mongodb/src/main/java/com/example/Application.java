package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableMongoRepositories(basePackages = { "com.eaxmple.repositories" })
public class Application {

    public static void main(String[] args) {

	SpringApplication.run(Application.class, args);

    }
}
