package com.example.ecommerce.modules.customers.dto;

import com.example.ecommerce.modules.customers.model.CustomerEnum;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerUpdateDTO(

        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nomeCompleto,

        @CPF(message = "CPF inválido")
        String cpf,

        @Email(message = "E-mail inválido")
        String email,

        @Pattern(
                regexp = "^\\d{10,11}$",
                message = "O telefone deve conter 10 ou 11 dígitos"
        )
        String telefone,

        CustomerEnum status,

        LocalDate dataAtualizacao

) {
}
