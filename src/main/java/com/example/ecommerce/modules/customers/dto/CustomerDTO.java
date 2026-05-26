package com.example.ecommerce.modules.customers.dto;

import java.time.LocalDate;

public record CustomerDTO(
        Long id,
        String nomeCompleto,
        String cpf,
        String email,
        String telefone,
        String senhaHash,
        String status,
        LocalDate dataCriacao,
        LocalDate dataAtualizacao
) {
}
