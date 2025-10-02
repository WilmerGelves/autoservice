package com.autoservice.integration;

import com.autoservice.domain.model.Appointment;
import com.autoservice.domain.service.AutoServiceManager;
import com.autoservice.infrastructure.repository.JpaAppointmentRepository;
import com.autoservice.infrastructure.repository.JpaCustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AutoServiceIntegrationTest {

    @Autowired
    private AutoServiceManager autoServiceManager;

    @Autowired
    private JpaAppointmentRepository appointmentRepository;

    @Autowired
    private JpaCustomerRepository customerRepository;

    @Test
    void createAppointment_IntegrationTest_ShouldPersistInDatabase() {
        // Arrange
        String customerEmail = "integration@test.com";
        LocalDateTime appointmentDate = LocalDateTime.now().plusDays(1);
        String serviceType = "Integración Test";

        // Act
        Appointment result = autoServiceManager.createAppointment(
                customerEmail, appointmentDate, serviceType);

        // Assert - Verificar persistencia en BD
        assertNotNull(result.getId());

        Appointment savedAppointment = appointmentRepository.findById(result.getId())
                .orElseThrow();

        assertEquals(customerEmail, savedAppointment.getCustomer().getEmail());
        assertEquals(serviceType, savedAppointment.getServiceType());

        // Verificar que el cliente también se persistió
        assertTrue(customerRepository.findByEmail(customerEmail).isPresent());
    }
}
