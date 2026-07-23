package com.example.ecommerce.modules.address.controller;

import com.example.ecommerce.modules.address.dto.AddressCreateDTO;
import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.address.dto.AddressUpdateDTO;
import com.example.ecommerce.modules.address.mapper.AddressMapper;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.service.AddressService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<?> createAddress(@Valid @RequestBody AddressCreateDTO addressCreate) {
        AddressCreateDTO address = addressService.criarAddress(addressCreate);

        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }

    @GetMapping
    public ResponseEntity<?> listAddresses(){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAll());
    }

    @GetMapping("/{idAddress}")
    public ResponseEntity<?> getAddress(@Positive(message = "O ID do Address tem que ser maior que 0")  @PathVariable Integer idAddress){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findById(idAddress));
    }

    @GetMapping("/user/{idUsuario}")
    public ResponseEntity<?> getAddressByUsuarioId(@Positive(message = "O ID do Customer tem que ser maior que 0") @PathVariable Integer idUsuario){
        List<Address> address = addressService.findByUsuarioId(idUsuario);
        List<AddressListDTO> addressListDTO = new ArrayList<>();
        address.forEach(addres -> {
            addressListDTO.add(AddressMapper.toAddressList(addres));
        });

        return ResponseEntity.status(HttpStatus.OK).body(addressListDTO.stream().toList());
    }

    @DeleteMapping("/{idAddress}")
    public ResponseEntity<?> deleteAddress(@Positive(message = "O ID do Address tem que ser maior que 0") @PathVariable Integer idAddress){
        addressService.deletarById(idAddress);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idAddress}/customer/{idCustomer}")
    public ResponseEntity<?> updateAddress(@Valid @RequestBody AddressUpdateDTO addressUpdateDTO, @Positive(message = "O ID do Address tem que ser maior que 0")  @PathVariable Integer idAddress, @Positive(message = "O ID do Customer tem que ser maior que 0")  @PathVariable Integer idCustomer){
        AddressUpdateDTO dto = addressService.alterAddressById(idAddress, idCustomer, addressUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
