package com.example.ecommerce.modules.customers.controller;

import com.example.ecommerce.modules.customers.dto.CustomerCreateDTO;
import com.example.ecommerce.modules.customers.dto.CustomerListDTO;
import com.example.ecommerce.modules.customers.dto.CustomerUpdateDTO;
import com.example.ecommerce.modules.customers.mapper.CustomerMapper;
import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.customers.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerCreateDTO dto) {
        Customers customer = customerService.criarUsuario(dto);
        CustomerListDTO customerCreateDTO = CustomerMapper.toCustomerListResponseDTO(customer);
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
    public ResponseEntity<CustomerListDTO> getIdUser(@Positive(message = "O ID do Customer tem que ser maior que 0") @PathVariable Integer idCustomer){
        return ResponseEntity.status(HttpStatus.OK).body((customerService.buscarUsuarioPorId(idCustomer)));
    }

    @PatchMapping("/{idCustomer}")
    public ResponseEntity<CustomerUpdateDTO>  updateCustomer(@Positive(message = "O ID do Customer tem que ser maior que 0") @PathVariable Integer idCustomer, @Valid @RequestBody CustomerUpdateDTO dto) {
        Customers customer = customerService.atualizarUsuarioPorId(idCustomer, dto);
        CustomerUpdateDTO customerCreateDTO = new CustomerUpdateDTO(
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
