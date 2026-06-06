package com.example.ecommerce.modules.customers.dto;

import com.example.ecommerce.modules.customers.model.CustomerEnum;

import java.time.LocalDate;

public record CustomerCreateDTO(
        Integer id,
        String nomeCompleto,
        String cpf,
        String email,
        String telefone,
        String senhaHash,
        CustomerEnum status,
        LocalDate dataCriacao,
        LocalDate dataAtualizacao
) {
}
