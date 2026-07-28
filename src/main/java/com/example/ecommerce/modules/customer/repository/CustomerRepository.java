package com.example.ecommerce.modules.customer.repository;

import com.example.ecommerce.modules.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
