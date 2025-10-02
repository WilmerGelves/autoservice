package com.autoservice.domain;

import com.autoservice.domain.service.TimeProvider;

import java.time.LocalDateTime;

public class MockTimeProvider implements TimeProvider {
    private LocalDateTime fixedTime;

    public MockTimeProvider(LocalDateTime fixedTime) {
        this.fixedTime = fixedTime;
    }

    public void setTime(LocalDateTime time) {
        this.fixedTime = time;
    }

    @Override
    public LocalDateTime now() {
        return fixedTime;
    }
}
