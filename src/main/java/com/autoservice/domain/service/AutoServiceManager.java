package com.autoservice.domain.service;

import com.autoservice.domain.model.Appointment;
import com.autoservice.domain.model.Customer;
import com.autoservice.domain.repository.AppointmentRepository;
import com.autoservice.domain.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class AutoServiceManager {

    private final TimeProvider timeProvider;
    private final EmailService emailService;
    private final AppointmentRepository appointmentRepository;
    private final CustomerRepository customerRepository;

    // Inyección de dependencias por constructor
    public AutoServiceManager(TimeProvider timeProvider,
                              EmailService emailService,
                              AppointmentRepository appointmentRepository,
                              CustomerRepository customerRepository) {
        this.timeProvider = timeProvider;
        this.emailService = emailService;
        this.appointmentRepository = appointmentRepository;
        this.customerRepository = customerRepository;
    }

    public Appointment createAppointment(String customerEmail,
                                         LocalDateTime appointmentDate,
                                         String serviceType) {

        // Validaciones
        if (appointmentDate.isBefore(timeProvider.now())) {
            throw new IllegalArgumentException("La fecha de cita no puede ser en el pasado");
        }

        // Buscar o crear cliente
        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseGet(() -> {
                    Customer newCustomer = new Customer(customerEmail,
                            customerEmail.split("@")[0],
                            "");
                    return customerRepository.save(newCustomer);
                });

        // Crear cita
        Appointment appointment = new Appointment(customer, appointmentDate, serviceType);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Enviar email (si el servicio está disponible)
        if (emailService.isAvailable()) {
            String subject = "Cita Programada - AutoService";
            String body = String.format(
                    "Estimado %s,\nSu cita para %s ha sido programada para el %s",
                    customer.getName(),
                    serviceType,
                    appointmentDate.toString()
            );
            emailService.sendEmail(customerEmail, subject, body);
        }

        return savedAppointment;
    }

    public Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newDate) {
        Appointment appointment = appointmentRepository.findAppointmentById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        if (newDate.isBefore(timeProvider.now())) {
            throw new IllegalArgumentException("La nueva fecha no puede ser en el pasado");
        }

        appointment.setAppointmentDate(newDate);
        appointment.setStatus("RESCHEDULED");

        return appointmentRepository.save(appointment);
    }
}
