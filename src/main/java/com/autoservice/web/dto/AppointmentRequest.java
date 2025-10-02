package com.autoservice.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record AppointmentRequest(
        @Email(message = "Email debe ser válido")
        @NotBlank(message = "Email es requerido")
        String customerEmail,

        @Future(message = "La fecha de cita debe ser en el futuro")
        LocalDateTime appointmentDate,

        @NotBlank(message = "El tipo de servicio es requerido")
        String serviceType
) {}
