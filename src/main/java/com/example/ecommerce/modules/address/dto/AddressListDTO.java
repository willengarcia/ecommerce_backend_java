package com.example.ecommerce.modules.address.dto;

import com.example.ecommerce.modules.address.model.AddressEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AddressListDTO(
        Integer id,

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

        Boolean enderecoPrincipal,

        LocalDate dataCriacao,

        LocalDate dataAtualizacao
) {
}
