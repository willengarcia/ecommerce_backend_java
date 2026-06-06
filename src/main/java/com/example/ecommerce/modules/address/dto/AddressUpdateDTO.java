package com.example.ecommerce.modules.address.dto;

import com.example.ecommerce.modules.address.model.AddressEnum;

import java.time.LocalDate;

public record AddressUpdateDTO(
        String nomeDestinatario,
        String numero,
        String complemento,
        String referencia,
        AddressEnum tipoEndereco,
        String enderecoPrincipal,
        LocalDate dataAtualizacao
) {
}
