package com.example.ecommerce.modules.address.controller;

import com.example.ecommerce.modules.address.dto.AddressCreateDTO;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<?> createAddress( @RequestBody Address addressCreate) {
        try{
            Address address = addressService.criarAddress(addressCreate);

            AddressCreateDTO dto = new AddressCreateDTO(
                    address.getNomeEndereco(),
                    address.getNomeDestinatario(),
                    address.getCep(),
                    address.getRua(),
                    address.getNumero(),
                    address.getCidade(),
                    address.getBairro(),
                    address.getEstado(),
                    address.getEnderecoPrincipal(),
                    address.getReferencia(),
                    address.getTipoEndereco(),
                    address.getEnderecoPrincipal(),
                    address.getUsuario().getId(),
                    address.getDataCriacao(),
                    address.getDataAtualizacao()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listAddresses(){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAll());
    }

}
