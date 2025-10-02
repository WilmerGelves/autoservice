package com.autoservice.infrastructure.service;

import com.autoservice.domain.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class RealEmailService implements EmailService {

    @Override
    public void sendEmail(String to, String subject, String body) {
        // Implementación real de envío de email
        System.out.printf("Enviando email a: %s%n", to);
        System.out.printf("Asunto: %s%n", subject);
        System.out.printf("Cuerpo: %s%n", body);
        // Aquí iría la lógica real de envío (SMTP, etc.)
    }

    @Override
    public boolean isAvailable() {
        // Verificar si el servicio de email está disponible
        return true;
    }
}