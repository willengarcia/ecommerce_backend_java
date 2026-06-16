package com.example.ecommerce.modules.order.dto;

import com.example.ecommerce.modules.address.dto.AddressListDTO;

public record OrderEditDTO(
        AddressListDTO addressId
) {
}
