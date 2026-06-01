package com.example.ecommerce.modules.cart.repository;

import com.example.ecommerce.modules.cart.model.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {

}
