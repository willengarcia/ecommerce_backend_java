package com.example.ecommerce.modules.order.dto;

import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.customers.dto.CustomerResponseDTO;
import com.example.ecommerce.modules.order.model.OrderEnum;

import java.math.BigDecimal;

public record OrderResponseDTO(
        Long orderId,
        BigDecimal valorTotal,
        OrderEnum status,
        CustomerResponseDTO customer,
        AddressListDTO address,
        java.util.stream.Stream<OrderItemResponseDTO> items
) {
}
