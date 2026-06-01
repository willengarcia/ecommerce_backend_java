package com.example.ecommerce.modules.cart.repository;

import com.example.ecommerce.modules.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
