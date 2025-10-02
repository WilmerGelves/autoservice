package com.autoservice.infrastructure.config;

import com.autoservice.domain.MockTimeProvider;
import com.autoservice.domain.SpyEmailService;
import com.autoservice.domain.service.EmailService;
import com.autoservice.domain.service.TimeProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public TimeProvider testTimeProvider() {
        return new MockTimeProvider(LocalDateTime.of(2024, 1, 1, 10, 0));
    }

    @Bean
    @Primary
    public EmailService testEmailService() {
        return new SpyEmailService();
    }
}
