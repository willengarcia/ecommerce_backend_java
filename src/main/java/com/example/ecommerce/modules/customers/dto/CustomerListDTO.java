package com.example.ecommerce.modules.customers.dto;

import com.example.ecommerce.modules.address.dto.AddressListDTO;

import java.time.LocalDate;
import java.util.List;

public record CustomerListDTO(
        Integer id,
        String nomeCompleto,
        String cpf,
        String email,
        String telefone,
        String status,
        List<AddressListDTO> addresses,
        LocalDate dataCriacao,
        LocalDate dataAtualizacao
) {
}
