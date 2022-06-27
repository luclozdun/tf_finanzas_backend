package com.api.finanzas.security.repository;

import java.util.Optional;

import com.api.finanzas.security.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> getCustomerById(Long id);

    Optional<Customer> getCustomerByDni(String dni);

    Optional<Customer> getCustomerByEmail(String email);

    Optional<Customer> getCustomerByUsername(String username);
}
