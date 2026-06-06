package com.example.ecommerce.modules.address.dto;

import com.example.ecommerce.modules.address.model.AddressEnum;

import java.time.LocalDate;

public record AddressCreateDTO(
        String nomeEndereco,
        String nomeDestinatario,
        String cep,
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String referencia,
        AddressEnum tipoEndereco,
        String enderecoPrincipal,
        Integer usuario_id,
        LocalDate dataCriacao,
        LocalDate dataAtualizacao
) {
}
