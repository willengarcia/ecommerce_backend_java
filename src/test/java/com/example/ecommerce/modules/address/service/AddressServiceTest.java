package com.example.ecommerce.modules.address.service;

import com.example.ecommerce.modules.address.dto.AddressCreateDTO;
import com.example.ecommerce.modules.address.exception.InvalidZipCodeException;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.model.AddressEnum;
import com.example.ecommerce.modules.address.repository.AddressRepository;
import com.example.ecommerce.modules.customer.exception.CustomerNotFoundException;
import com.example.ecommerce.modules.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock // -> duble (finge ser algo)
    private AddressRepository  addressRepository;
    @Mock // -> duble (finge ser algo)
    private CustomerRepository customerRepository;

    @InjectMocks 
    private AddressService addressService;

    @Test
    @DisplayName("Deve retornar a exception de InvalidZipCodeException")
    void returnExceptionAddressInvalidZipCode() {
        AddressCreateDTO dto = new AddressCreateDTO(
                "teste unity",
                "a",
                "6703012",
                "teste",
                "S/A",
                "Ananindeua",
                "Centro",
                "PA",
                "Próximo ao supermercado",
                null,
                AddressEnum.TRABALHO,
                true,
                1
        );
        // Formato de CEP inválido -  Lançar o erro que está destinado a sair para o usuário.
        AddressService service = new AddressService(addressRepository, customerRepository);
        InvalidZipCodeException exception = Assertions.assertThrows(
                InvalidZipCodeException.class,
                () -> service.createAddress(dto)
        );

        // Saída esperada
        Assertions.assertEquals("Formato de CEP inválido, deve conter 8 números ou caso não tenha, informar 'S/N' " +
                "\nEx: 00000000 ou 99999-000", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar a exception de CustomerNotFoundException")
    void returnExceptionAddressCustomerNotFound(){
        AddressCreateDTO dto = new AddressCreateDTO(
                "teste unity",
                "a",
                "67033012",
                "teste",
                "S/A",
                "Ananindeua",
                "Centro",
                "PA",
                "Próximo ao supermercado",
                null,
                AddressEnum.TRABALHO,
                true,
                175
        );
        when(customerRepository.findById(dto.usuarioId()))
                .thenReturn(Optional.empty());
        // Usuário não existente
        CustomerNotFoundException exception = Assertions.assertThrows(
                CustomerNotFoundException.class,
                () -> addressService.createAddress(dto)
        );

        // Saída esperada
        Assertions.assertEquals("Usuário não encontrado", exception.getMessage());
        verify(customerRepository).findById(dto.usuarioId());
        verify(addressRepository, never()).save(any(Address.class));
    }
}