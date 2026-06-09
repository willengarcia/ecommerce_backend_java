package com.example.ecommerce.modules.order.controller;

import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.category.model.OrderEnum;
import com.example.ecommerce.modules.customers.dto.CustomerListDTO;
public record OrderResponseDTO(
        Long orderId,
        Float valorTotal,
        OrderEnum status
        //CustomerListDTO usuario,
        //AddressListDTO address
) {
}
