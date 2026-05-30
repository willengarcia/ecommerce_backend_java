package com.example.ecommerce.modules.customers.dto;

import java.time.LocalDate;

public record CustomerUpdateDTO(
        Integer id,
        String nomeCompleto,
        String cpf,
        String email,
        String telefone,
        String status,
        LocalDate dataAtualizacao
) {
}
