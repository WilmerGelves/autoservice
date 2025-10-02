package com.autoservice.domain;

import com.autoservice.domain.model.Appointment;
import com.autoservice.domain.model.Customer;
import com.autoservice.domain.repository.AppointmentRepository;
import com.autoservice.domain.repository.CustomerRepository;
import com.autoservice.domain.service.AutoServiceManager;
import com.autoservice.domain.service.EmailService;
import com.autoservice.domain.service.TimeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutoServiceManagerTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    private MockTimeProvider timeProvider;
    private SpyEmailService emailService;
    private AutoServiceManager autoServiceManager;

    private LocalDateTime fixedTime;
    private LocalDateTime futureTime;

    @BeforeEach
    void setUp() {
        fixedTime = LocalDateTime.of(2024, 1, 1, 10, 0);
        futureTime = LocalDateTime.of(2024, 1, 2, 10, 0);

        timeProvider = new MockTimeProvider(fixedTime);
        emailService = new SpyEmailService();

        autoServiceManager = new AutoServiceManager(
                (TimeProvider) timeProvider, (EmailService) emailService, appointmentRepository, customerRepository
        );
    }

    @Test
    void createAppointment_WithNewCustomer_ShouldCreateAppointmentAndSendEmail() {
        // Arrange
        String customerEmail = "cliente@test.com";
        String serviceType = "Cambio de aceite";

        when(customerRepository.findByEmail(customerEmail))
                .thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(appointmentRepository.save(any(Appointment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Appointment result = autoServiceManager.createAppointment(
                customerEmail, futureTime, serviceType);

        // Assert
        assertNotNull(result);
        assertEquals(customerEmail, result.getCustomer().getEmail());
        assertEquals(serviceType, result.getServiceType());
        assertEquals("SCHEDULED", result.getStatus());

        // Verificar que se envió el email
        assertEquals(1, emailService.getSendCallCount());
        assertEquals(customerEmail, emailService.getLastRecipient());

        // Verificar interacciones con los mocks
        verify(customerRepository).findByEmail(customerEmail);
        verify(customerRepository).save(any(Customer.class));
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    void createAppointment_WithPastDate_ShouldThrowException() {
        // Arrange
        String customerEmail = "cliente@test.com";
        LocalDateTime pastDate = fixedTime.minusDays(1);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> autoServiceManager.createAppointment(customerEmail, pastDate, "Lavado")
        );

        assertEquals("La fecha de cita no puede ser en el pasado", exception.getMessage());

        // Verificar que NO se envió email
        assertEquals(0, emailService.getSendCallCount());
    }

    @Test
    void createAppointment_WithExistingCustomer_ShouldReuseCustomer() {
        // Arrange
        String customerEmail = "existente@test.com";
        Customer existingCustomer = new Customer(customerEmail, "Cliente Existente", "123456789");
        existingCustomer.setId(1L);

        when(customerRepository.findByEmail(customerEmail))
                .thenReturn(Optional.of(existingCustomer));
        when(appointmentRepository.save(any(Appointment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Appointment result = autoServiceManager.createAppointment(
                customerEmail, futureTime, "Alineación");

        // Assert
        assertNotNull(result);
        assertEquals(existingCustomer, result.getCustomer());

        // Verificar que NO se creó un nuevo cliente
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void rescheduleAppointment_WithValidDate_ShouldUpdateAppointment() {
        // Arrange
        Long appointmentId = 1L;
        LocalDateTime newDate = futureTime.plusDays(1);

        Customer customer = new Customer("test@test.com", "Test", "123");
        Appointment existingAppointment = new Appointment(customer, futureTime, "Service");
        existingAppointment.setId(appointmentId);

        when(appointmentRepository.findAppointmentById(appointmentId))
                .thenReturn(Optional.of(existingAppointment));
        when(appointmentRepository.save(any(Appointment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Appointment result = autoServiceManager.rescheduleAppointment(appointmentId, newDate);

        // Assert
        assertNotNull(result);
        assertEquals(newDate, result.getAppointmentDate());
        assertEquals("RESCHEDULED", result.getStatus());

        verify(appointmentRepository).save(existingAppointment);
    }
}
