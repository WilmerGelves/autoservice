package com.autoservice.domain;

import com.autoservice.domain.model.Appointment;
import com.autoservice.domain.repository.AppointmentRepository;

import java.util.*;

public class FakeAppointmentRepository implements AppointmentRepository {
    private Map<Long, Appointment> appointments = new HashMap<>();
    private Long currentId = 1L;

    @Override
    public Appointment save(Appointment appointment) {
        if (appointment.getId() == null) {
            appointment.setId(currentId++);
        }
        appointments.put(appointment.getId(), appointment);
        return appointment;
    }

    @Override
    public Optional<Appointment> findAppointmentById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Appointment> findByCustomerEmail(String email) {
        return appointments.values().stream()
                .filter(a -> a.getCustomer().getEmail().equals(email))
                .toList();
    }

    @Override
    public void delete(Appointment appointment) {
        appointments.remove(appointment.getId());
    }

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(appointments.values());
    }
}
