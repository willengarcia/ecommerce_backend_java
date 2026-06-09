package com.example.ecommerce.modules.order.dto;

import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.category.model.OrderEnum;
import com.example.ecommerce.modules.customers.model.Customers;

public record OrderCreateDTO(
        Float valorTotal,
        OrderEnum status,
        Customers usuarioId,
        Address addressId
) {
}
