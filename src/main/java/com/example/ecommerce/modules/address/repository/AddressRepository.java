package com.example.ecommerce.modules.address.repository;

import com.example.ecommerce.modules.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AddressRepository extends JpaRepository<Address, Integer> {
    public List<Address> findByUsuarioId(Integer id);
}
