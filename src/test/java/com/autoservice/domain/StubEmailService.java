package com.autoservice.domain;

import com.autoservice.domain.service.EmailService;

import java.util.ArrayList;
import java.util.List;

public class StubEmailService implements EmailService {
    private List<String> sentEmails = new ArrayList<>();
    private boolean available = true;

    @Override
    public void sendEmail(String to, String subject, String body) {
        sentEmails.add(String.format("To: %s, Subject: %s", to, subject));
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<String> getSentEmails() {
        return sentEmails;
    }

    public void clear() {
        sentEmails.clear();
    }
}