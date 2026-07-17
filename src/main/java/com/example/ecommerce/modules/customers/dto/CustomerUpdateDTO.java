package com.example.ecommerce.modules.customers.dto;

import com.example.ecommerce.modules.customers.model.CustomerEnum;

import java.time.LocalDate;

public record CustomerUpdateDTO(
        Integer id,
        String nomeCompleto,
        String cpf,
        String email,
        String telefone,
        CustomerEnum status,
        LocalDate dataAtualizacao
) {
}
