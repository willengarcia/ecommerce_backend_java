package com.example.ecommerce.modules.cart.dto;

import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.customers.dto.CustomerResponseDTO;

import java.util.List;

public record CartDetailsResponse(
        List<CartItemResponseDTO> cartItems,
        CartResponseDTO cart,
        CustomerResponseDTO customer,
        AddressListDTO address
) {
}
