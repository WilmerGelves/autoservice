package com.autoservice.domain.repository;

import com.autoservice.domain.model.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findById(Long id);
}