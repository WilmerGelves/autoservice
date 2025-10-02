package com.autoservice.domain;

import com.autoservice.domain.service.EmailService;

public class SpyEmailService implements EmailService {
    private int sendCallCount = 0;
    private String lastRecipient;
    private String lastSubject;
    private boolean available = true;

    @Override
    public void sendEmail(String to, String subject, String body) {
        this.sendCallCount++;
        this.lastRecipient = to;
        this.lastSubject = subject;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    public int getSendCallCount() {
        return sendCallCount;
    }

    public String getLastRecipient() {
        return lastRecipient;
    }

    public String getLastSubject() {
        return lastSubject;
    }
}