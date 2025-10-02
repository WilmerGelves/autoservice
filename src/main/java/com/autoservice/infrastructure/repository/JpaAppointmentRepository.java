package com.autoservice.infrastructure.repository;

import com.autoservice.domain.model.Appointment;
import com.autoservice.domain.repository.AppointmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaAppointmentRepository extends JpaRepository<Appointment, Long>, AppointmentRepository {

    @Query("SELECT a FROM Appointment a WHERE a.customer.email = :email")
    List<Appointment> findByCustomerEmail(@Param("email") String email);

    // Los métodos de la interfaz AppointmentRepository se heredan automáticamente
    // gracias a JpaRepository
}
