package com.example.ecommerce.modules.address.service;

import com.example.ecommerce.modules.address.dto.AddressCreateDTO;
import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.repository.AddressRepository;
import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.customers.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public Address criarAddress(Address address) {
        Customers customers = customerRepository.findById(address.getUsuario().getId()).orElseThrow();
        if(address.getCep().isEmpty() || address.getBairro().isEmpty() || address.getCidade().isEmpty() || address.getNumero().isEmpty() || address.getEstado().isEmpty()){
            throw new RuntimeException("É necessário informar a Cidade, Bairro, Número, Estado, Rua, CEP");
        } else if (customers.isStatus().equals("INATIVO")) {
            throw new RuntimeException("Usuário Inativo");
        } else {
            address.setDataCriacao(LocalDate.now());
            address.setDataAtualizacao(LocalDate.now());
            return addressRepository.save(address);
        }
    }

    public List<AddressListDTO> findAll(){
        List<Address> addresses = addressRepository.findAll();
        return  addresses.stream().map(
                c -> new AddressListDTO(
                        c.getId(),
                        c.getNomeEndereco(),
                        c.getNomeDestinatario(),
                        c.getCep(),
                        c.getRua(),
                        c.getNumero(),
                        c.getComplemento(),
                        c.getBairro(),
                        c.getCidade(),
                        c.getEstado(),
                        c.getReferencia(),
                        c.getTipoEndereco(),
                        c.getEnderecoPrincipal(),
                        c.getDataCriacao(),
                        c.getDataAtualizacao()
                )
        ).toList();
    }
}
