package com.autoservice.domain.repository;

import com.autoservice.domain.model.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Appointment save(Appointment appointment);
    Optional<Appointment> findAppointmentById(Long id);
    List<Appointment> findByCustomerEmail(String email);
    void delete(Appointment appointment);
    List<Appointment> findAll();
}
