package com.miguelpazatto.orderapi.repositories;

import com.miguelpazatto.orderapi.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
