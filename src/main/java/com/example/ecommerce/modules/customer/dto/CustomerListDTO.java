package com.example.ecommerce.modules.customer.dto;

import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.customer.model.CustomerEnum;

import java.time.LocalDate;
import java.util.List;

public record CustomerListDTO(
        Integer id,
        String nomeCompleto,
        String cpf,
        String email,
        String telefone,
        CustomerEnum status,
        List<AddressListDTO> addresses,
        LocalDate dataCriacao,
        LocalDate dataAtualizacao
) {
}
