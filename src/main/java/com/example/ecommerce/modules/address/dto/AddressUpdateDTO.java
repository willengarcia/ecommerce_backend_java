package com.example.ecommerce.modules.address.dto;

import com.example.ecommerce.modules.address.model.AddressEnum;

import java.time.LocalDate;

public record AddressUpdateDTO(
        String nomeEndereco,
        String nomeDestinatario,
        String cep,
        String rua,
        String numero,
        String cidade,
        String bairro,
        String estado,
        String complemento,
        String referencia,
        AddressEnum tipoEndereco,
        Boolean enderecoPrincipal
) {
}
