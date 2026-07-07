package com.example.ecommerce.modules.customers.dto;

import com.example.ecommerce.modules.customers.model.CustomerEnum;

public record CustomerResponseDTO(
        Integer id,
        String nomeCompleto,
        String cpf,
        String email,
        String telefone,
        CustomerEnum status
) {
}
