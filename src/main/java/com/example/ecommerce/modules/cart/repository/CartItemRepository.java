package com.example.ecommerce.modules.cart.repository;

import com.example.ecommerce.modules.cart.model.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {

    CartItem findByCarroIdAndProductId(int idCartItem, Integer idProduct);

    List<CartItem> findAllByCarroId(Integer cartId);

    CartItem findByCarroIdAndId(Integer cartId, Integer cartItemId);
}
