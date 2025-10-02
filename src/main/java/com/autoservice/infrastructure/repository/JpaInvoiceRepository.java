package com.autoservice.infrastructure.repository;


import com.autoservice.domain.model.Invoice;
import com.autoservice.domain.repository.InvoiceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaInvoiceRepository extends JpaRepository<Invoice, Long>, InvoiceRepository {

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    @Query("SELECT i FROM Invoice i WHERE i.appointment.id = :appointmentId")
    Optional<Invoice> findByAppointmentId(@Param("appointmentId") Long appointmentId);
}
