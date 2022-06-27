package com.api.finanzas.security.service;

import java.util.List;

import com.api.finanzas.security.dto.AuthenticateRequest;
import com.api.finanzas.security.dto.CustomerRequest;
import com.api.finanzas.security.dto.CustomerResponse;
import com.api.finanzas.security.entity.Customer;

public interface CustomerService {

    List<Customer> getAll();

    CustomerResponse getById(Long id);

    CustomerResponse getByUsername(String username);

    CustomerResponse getByEmail(String email);

    CustomerResponse getByDni(String dni);

    CustomerResponse create(CustomerRequest request);

    CustomerResponse update(CustomerRequest request, Long id);

    CustomerResponse authenticate(AuthenticateRequest request);

    void delete(Long id);

}
