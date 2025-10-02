package com.autoservice.infrastructure.repository;

import com.autoservice.domain.model.Customer;
import com.autoservice.domain.repository.CustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCustomerRepository extends JpaRepository<Customer, Long>, CustomerRepository {
    Optional<Customer> findByEmail(String email);
}
