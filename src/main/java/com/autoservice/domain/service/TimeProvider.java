package com.autoservice.domain.service;

import java.time.LocalDateTime;

public interface TimeProvider {
    LocalDateTime now();
}
