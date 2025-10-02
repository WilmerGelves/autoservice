package com.autoservice.domain.repository;

import com.autoservice.domain.model.Invoice;

import java.util.Optional;

public interface InvoiceRepository {
    Invoice save(Invoice invoice);
    Optional<Invoice> findById(Long id);
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    Optional<Invoice> findByAppointmentId(Long appointmentId);
}