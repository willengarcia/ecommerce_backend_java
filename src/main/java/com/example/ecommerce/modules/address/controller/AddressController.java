package com.example.ecommerce.modules.address.controller;

import com.example.ecommerce.modules.address.dto.AddressCreateDTO;
import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.address.dto.AddressUpdateDTO;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
                    address.getComplemento(),
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

    @GetMapping("/{idAddress}")
    public ResponseEntity<?> getAddress(@PathVariable Integer idAddress){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findById(idAddress));
    }

    @GetMapping("/user/{idUsuario}")
    public ResponseEntity<?> getAddressByUsuarioId(@PathVariable Integer idUsuario){
        List<Address> address = addressService.findByUsuarioId(idUsuario);
        List<AddressListDTO> addressListDTO = new ArrayList<>();
        address.forEach(addres -> {
            addressListDTO.add(new AddressListDTO(
                    addres.getId(),
                    addres.getNomeEndereco(),
                    addres.getNomeDestinatario(),
                    addres.getCep(),
                    addres.getRua(),
                    addres.getNumero(),
                    addres.getComplemento(),
                    addres.getBairro(),
                    addres.getCidade(),
                    addres.getEstado(),
                    addres.getReferencia(),
                    addres.getTipoEndereco(),
                    addres.getEnderecoPrincipal(),
                    addres.getDataCriacao(),
                    addres.getDataAtualizacao()
            ));
        });

        return ResponseEntity.status(HttpStatus.OK).body(addressListDTO.stream().toList());
    }

    @DeleteMapping("/{idAddress}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer idAddress){
        addressService.deletarById(idAddress);
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
    }

    @PatchMapping("/{idAddress}/customer/{idCustomer}")
    public ResponseEntity<?> updateAddress(@RequestBody AddressUpdateDTO addressUpdateDTO, @PathVariable Integer idAddress,  @PathVariable Integer idCustomer){
        AddressUpdateDTO dto = addressService.alterAddressById(idAddress, idCustomer, addressUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
