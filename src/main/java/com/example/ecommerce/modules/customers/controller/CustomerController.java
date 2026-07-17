package com.example.ecommerce.modules.customers.controller;

import com.example.ecommerce.modules.customers.dto.CustomerCreateDTO;
import com.example.ecommerce.modules.customers.dto.CustomerListDTO;
import com.example.ecommerce.modules.customers.dto.CustomerUpdateDTO;
import com.example.ecommerce.modules.customers.mapper.CustomerMapper;
import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.customers.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerCreateDTO dto) {
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
    }

    @GetMapping
    public ResponseEntity<List<?>> getAllCustomers() {
        List<Customers> customers = customerService.findAll();
        List<CustomerListDTO> response = customers.stream()
                .map(CustomerMapper::toCustomerListResponseDTO)
                .toList();

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{idCustomer}")
    public ResponseEntity<CustomerListDTO> getIdUser(@PathVariable Integer idCustomer){
        return ResponseEntity.status(HttpStatus.OK).body((customerService.buscarUsuarioPorId(idCustomer)));
    }

    @PatchMapping("/{idCustomer}")
    public ResponseEntity<CustomerUpdateDTO>  updateCustomer(@PathVariable Integer idCustomer, @RequestBody CustomerUpdateDTO dto) {
        Customers customer = customerService.atualizarUsuarioPorId(idCustomer, dto);
        CustomerUpdateDTO customerCreateDTO = new CustomerUpdateDTO(
                customer.getId(),
                customer.getNomeCompleto(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getTelefone(),
                customer.getStatus(),
                customer.getDataAtualizacao()
        );
        return ResponseEntity.status(HttpStatus.OK).body(customerCreateDTO);
    }

}
