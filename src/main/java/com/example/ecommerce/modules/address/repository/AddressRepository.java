package com.example.ecommerce.modules.address.repository;

import com.example.ecommerce.modules.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
