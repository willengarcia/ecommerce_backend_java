package com.example.ecommerce.modules.customer.dto;

import com.example.ecommerce.modules.customer.model.CustomerEnum;

public record CustomerResponseDTO(
        Integer id,
        String nomeCompleto,
        String cpf,
        String email,
        String telefone,
        CustomerEnum status
) {
}
