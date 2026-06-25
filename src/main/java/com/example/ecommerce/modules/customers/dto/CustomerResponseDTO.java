package com.example.ecommerce.modules.customers.dto;

import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.customers.model.CustomerEnum;

import java.time.LocalDate;
import java.util.List;

public record CustomerResponseDTO(
        Integer id,
        String nomeCompleto,
        String cpf,
        String email,
        String telefone,
        CustomerEnum status
) {
}
