package com.lionsaid.admin.web;

import com.lionsaid.admin.web.config.AuditorConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@AllArgsConstructor
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@SpringBootApplication(scanBasePackages = "com.lionsaid.admin.web")

public class AdminRun {

    private  final  AuditorConfiguration auditorConfiguration;
    public static void main(String[] args) {
        SpringApplication.run(AdminRun.class, args);
    }

    // 注册 AuditorAware 接口的实现类
    @Bean
    public AuditorAware<String> auditorProvider() {
        return auditorConfiguration; // 这里需要提供 redisTemplate
    }
}
