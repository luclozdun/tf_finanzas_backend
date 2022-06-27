package com.api.finanzas.security.controller;

import java.util.List;

import com.api.finanzas.security.dto.AuthenticateRequest;
import com.api.finanzas.security.dto.CustomerRequest;
import com.api.finanzas.security.dto.CustomerResponse;
import com.api.finanzas.security.entity.Customer;
import com.api.finanzas.security.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    private ResponseEntity<List<Customer>> getAll() {
        var response = customerService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<CustomerResponse> getById(@PathVariable("id") Long id) {
        var response = customerService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    private ResponseEntity<CustomerResponse> getByUsername(@PathVariable("username") String username) {
        var response = customerService.getByUsername(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    private ResponseEntity<CustomerResponse> getByEmail(@PathVariable("email") String email) {
        var response = customerService.getByEmail(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/dni/{dni}")
    private ResponseEntity<CustomerResponse> getByDni(@PathVariable("dni") String dni) {
        var response = customerService.getByDni(dni);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<CustomerResponse> create(@RequestBody CustomerRequest request) {
        var response = customerService.create(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<CustomerResponse> update(@PathVariable("id") Long id,
            @RequestBody CustomerRequest request) {
        var response = customerService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Long id) {
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    private ResponseEntity<CustomerResponse> authenticate(@RequestBody AuthenticateRequest request) {
        var response = customerService.authenticate(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
