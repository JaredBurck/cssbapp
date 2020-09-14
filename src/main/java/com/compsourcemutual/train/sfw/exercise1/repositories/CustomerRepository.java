package com.compsourcemutual.train.sfw.exercise1.repositories;

import com.compsourcemutual.train.sfw.exercise1.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);
    boolean existsByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);
}
/*
 * Author  : isfg
 * Modified: 4/19/2020 2:11 PM
 * Copyright 2020. All Rights Reserved by CompSource Mutual
 */
