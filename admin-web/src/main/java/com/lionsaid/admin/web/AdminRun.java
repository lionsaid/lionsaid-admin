package com.lionsaid.admin.web;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@AllArgsConstructor
@EnableJpaAuditing
public class AdminRun {

    public static void main(String[] args) {
        SpringApplication.run(AdminRun.class, args);
    }

}
