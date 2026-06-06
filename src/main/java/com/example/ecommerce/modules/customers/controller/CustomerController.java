package com.example.ecommerce.modules.customers.controller;

import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.customers.dto.CustomerCreateDTO;
import com.example.ecommerce.modules.customers.dto.CustomerListDTO;
import com.example.ecommerce.modules.customers.dto.CustomerUpdateDTO;
import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.customers.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerCreateDTO dto) {
        try{
            Customers customer = customerService.criarUsuario(dto);
            CustomerCreateDTO customerCreateDTO = new CustomerCreateDTO(
                    customer.getId(),
                    customer.getNomeCompleto(),
                    customer.getCpf(),
                    customer.getEmail(),
                    customer.getTelefone(),
                    customer.getSenhaHash(),
                    customer.isStatus(),
                    customer.getDataCriacao(),
                    customer.getDataAtualizacao()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(customerCreateDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<?>> getAllCustomers() {
        List<Customers> dto = customerService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(
                dto.stream()
                        .map(customer -> new CustomerListDTO(
                                customer.getId(),
                                customer.getNomeCompleto(),
                                customer.getCpf(),
                                customer.getEmail(),
                                customer.getTelefone(),
                                customer.isStatus(),
                                customer.getEnderecos()
                                        .stream()
                                        .map(address -> new AddressListDTO(
                                                address.getId(),
                                                address.getNomeEndereco(),
                                                address.getNomeDestinatario(),
                                                address.getCep(),
                                                address.getRua(),
                                                address.getNumero(),
                                                address.getComplemento(),
                                                address.getBairro(),
                                                address.getCidade(),
                                                address.getEstado(),
                                                address.getReferencia(),
                                                address.getTipoEndereco(),
                                                address.getTipoEndereco(),
                                                address.getDataCriacao(),
                                                address.getDataAtualizacao()
                                        ))
                                        .toList(),
                                customer.getDataCriacao(),
                                customer.getDataAtualizacao()
                        ))
                        .toList()
        );
    }
    
    @GetMapping("/{idCustomer}")
    public CustomerListDTO getIdUser(@PathVariable Integer idCustomer){
        return customerService.buscarUsuarioPorId(idCustomer);
    }

    @PutMapping("/{idCustomer}")
    public ResponseEntity<CustomerUpdateDTO>  updateCustomer(@PathVariable Integer idCustomer, @RequestBody CustomerUpdateDTO dto) {
        Customers customer = customerService.atualizarUsuarioPorId(idCustomer, dto);
        CustomerUpdateDTO customerCreateDTO = new CustomerUpdateDTO(
                customer.getId(),
                customer.getNomeCompleto(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getTelefone(),
                customer.isStatus().name(),
                customer.getDataAtualizacao()
        );
        return ResponseEntity.status(HttpStatus.OK).body(customerCreateDTO);
    }

}
