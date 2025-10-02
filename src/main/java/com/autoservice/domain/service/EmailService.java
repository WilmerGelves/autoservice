package com.autoservice.domain.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
    boolean isAvailable();
}
