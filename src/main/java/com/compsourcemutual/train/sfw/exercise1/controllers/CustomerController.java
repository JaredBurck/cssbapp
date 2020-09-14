package com.compsourcemutual.train.sfw.exercise1.controllers;

import com.compsourcemutual.train.sfw.exercise1.domain.Customer;
import com.compsourcemutual.train.sfw.exercise1.repositories.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @DeleteMapping(value = "/sfw/exercise1/customers/{customerId}")
    public void removeCustomer(@PathVariable Long customerId) {
        customerRepository.findById(customerId).ifPresentOrElse(
                // customer found
                customer -> customerRepository.deleteById(customerId),
                // customer not found
                () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }
        );
    }

    @GetMapping(value = "/sfw/exercise1/customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping(value = "/sfw/exercise1/customers/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/sfw/exercise1/customers/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @Valid @RequestBody Customer customerToUpdate) {
        // check for customer id present
        if (!customerRepository.existsById(customerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // check for duplicate
        customerRepository.findCustomerByFirstNameAndLastNameAndDateOfBirth(customerToUpdate.getFirstName(), customerToUpdate.getLastName(), customerToUpdate.getDateOfBirth())
                .ifPresent(customer -> {
                    if (!customer.getId().equals(customerId)) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT);
                    }
                });

        // retrieve this customer for update
        var customer = customerRepository.getOne(customerId);
        customer.setDateOfBirth(customerToUpdate.getDateOfBirth());
        customer.setLastName(customerToUpdate.getLastName());
        customer.setFirstName(customerToUpdate.getFirstName());
        customer = customerRepository.save(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @PostMapping(value = "/sfw/exercise1/customers")
    public ResponseEntity<Customer> postCustomer(@Valid @RequestBody Customer customer) {
        if (customerRepository.existsByFirstNameAndLastNameAndDateOfBirth(customer.getFirstName(), customer.getLastName(),
                customer.getDateOfBirth())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        Customer addedCustomer = customerRepository.save(customer);
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }
}
/*
 * Author  : isfg
 * Modified: 4/19/2020 2:12 PM
 * Copyright 2020. All Rights Reserved by CompSource Mutual
 */
