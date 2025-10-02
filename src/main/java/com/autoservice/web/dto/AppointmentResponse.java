package com.autoservice.web.dto;

import java.time.LocalDateTime;

public  record AppointmentResponse(
        Long id,
        String customerEmail,
        String customerName,
        LocalDateTime appointmentDate,
        String serviceType,
        String status,
        LocalDateTime createdAt
) {}
