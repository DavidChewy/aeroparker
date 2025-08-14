package com.example.aeroparkertechtest.repository;

import com.example.aeroparkertechtest.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
