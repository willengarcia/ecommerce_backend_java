package com.example.ecommerce.modules.order.dto;

import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.cart.model.Cart;

public record OrderCreateDTO(
        Cart cart,
        Address address,
        String metodoPagamento
) {
}
