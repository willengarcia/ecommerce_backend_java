package com.example.ecommerce.modules.customers.repository;

import com.example.ecommerce.modules.customers.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customers,Integer> {
}
