package com.example.ecommerce.modules.customers.dto;

import com.example.ecommerce.modules.customers.model.CustomerEnum;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;


public record CustomerCreateDTO(
        @NotBlank(message = "O nome completo é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nomeCompleto,

        @NotBlank(message = "O CPF é obrigatório")
        @CPF(message = "CPF inválido")
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "O telefone é obrigatório")
        @Pattern(
                regexp = "^\\d{10,11}$",
                message = "O telefone deve conter 10 ou 11 dígitos"
        )
        String telefone,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, max = 100, message = "A senha deve ter no mínimo 8 caracteres")
        String senhaHash,

        @NotNull(message = "O status é obrigatório")
        CustomerEnum status
) {
}
