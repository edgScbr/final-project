package com.applaudo.javatraining.finalproject.repositories;

import com.applaudo.javatraining.finalproject.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserName(String userName);
}
