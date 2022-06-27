package com.api.finanzas.security.service.impl;

import java.util.Date;
import java.util.List;

import com.api.finanzas.exception.ResourceNotFoundExceptionRequest;
import com.api.finanzas.security.dto.AuthenticateRequest;
import com.api.finanzas.security.dto.CustomerRequest;
import com.api.finanzas.security.dto.CustomerResponse;
import com.api.finanzas.security.entity.Customer;
import com.api.finanzas.security.repository.CustomerRepository;
import com.api.finanzas.security.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private CustomerResponse ConvertToResponse(Customer entity) {
        CustomerResponse response = CustomerResponse.builder()
                .id(entity.getId())
                .created(entity.getCreated())
                .dni(entity.getDni())
                .email(entity.getEmail())
                .name(entity.getName())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();

        return response;
    }

    private Customer ConvertToEntity(CustomerRequest request) {
        Customer entity = Customer.builder()
                .created(new Date())
                .dni(request.getDni())
                .email(request.getEmail())
                .name(request.getName())
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .build();

        return entity;
    }

    private Customer ConvertToEntity(CustomerRequest request, Long id) {
        Customer entity = Customer.builder()
                .id(id)
                .created(new Date())
                .dni(request.getDni())
                .email(request.getEmail())
                .name(request.getName())
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .build();

        return entity;
    }

    @Override
    public List<Customer> getAll() {
        var entities = customerRepository.findAll();
        return entities;
    }

    @Override
    public CustomerResponse getById(Long id) {
        var entity = customerRepository.getCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found by id"));

        var response = ConvertToResponse(entity);

        return response;
    }

    @Override
    public CustomerResponse getByUsername(String username) {
        var entity = customerRepository.getCustomerByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found by username"));

        var response = ConvertToResponse(entity);

        return response;
    }

    @Override
    public CustomerResponse getByEmail(String email) {
        var entity = customerRepository.getCustomerByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found by email"));

        var response = ConvertToResponse(entity);

        return response;
    }

    @Override
    public CustomerResponse getByDni(String dni) {
        var entity = customerRepository.getCustomerByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found by dni"));

        var response = ConvertToResponse(entity);

        return response;
    }

    @Override
    public CustomerResponse create(CustomerRequest request) {
        var entity = ConvertToEntity(request);

        var validEmail = customerRepository.getCustomerByEmail(request.getEmail()).orElse(null);

        if (validEmail != null) {
            throw new ResourceNotFoundExceptionRequest("Email is being used");
        }

        var validDni = customerRepository.getCustomerByDni(request.getDni()).orElse(null);

        if (validDni != null) {
            throw new ResourceNotFoundExceptionRequest("Dni is being used");
        }

        var validUsername = customerRepository.getCustomerByUsername(request.getUsername()).orElse(null);

        if (validUsername != null) {
            throw new ResourceNotFoundExceptionRequest("Username is being used");
        }

        try {
            customerRepository.save(entity);
            var response = ConvertToResponse(entity);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while saving customer");
        }
    }

    @Override
    public CustomerResponse update(CustomerRequest request, Long id) {
        var entity = customerRepository.getCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found by id"));

        entity = ConvertToEntity(request, id);

        var validEmail = customerRepository.getCustomerByEmail(request.getEmail()).orElse(null);

        if (validEmail != null) {
            throw new ResourceNotFoundExceptionRequest("Email is being used");
        }

        var validDni = customerRepository.getCustomerByDni(request.getDni()).orElse(null);

        if (validDni != null) {
            throw new ResourceNotFoundExceptionRequest("Dni is being used");
        }

        var validUsername = customerRepository.getCustomerByUsername(request.getUsername()).orElse(null);

        if (validUsername != null) {
            throw new ResourceNotFoundExceptionRequest("Username is being used");
        }

        try {
            customerRepository.save(entity);
            var response = ConvertToResponse(entity);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating customer");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting customer");
        }
    }

    @Override
    public CustomerResponse authenticate(AuthenticateRequest request) {
        var entity = new Customer();
        if (request.getType() == 0) {
            entity = customerRepository.getCustomerByUsername(request.getLogin())
                    .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found by username"));

        } else if (request.getType() == 1) {
            entity = customerRepository.getCustomerByDni(request.getLogin())
                    .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found by dni"));
        } else if (request.getType() == 2) {
            entity = customerRepository.getCustomerByEmail(request.getLogin())
                    .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found by email"));
        }
        var validPassword = encoder.matches(request.getPassword(), entity.getPassword());

        if (validPassword == false) {
            throw new ResourceNotFoundExceptionRequest("Invalid Credentials");
        }

        var response = ConvertToResponse(entity);
        return response;
    }

}
