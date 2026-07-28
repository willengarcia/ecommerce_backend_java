package com.example.ecommerce.modules.customer.controller;

import com.example.ecommerce.modules.customer.dto.CustomerCreateDTO;
import com.example.ecommerce.modules.customer.dto.CustomerListDTO;
import com.example.ecommerce.modules.customer.dto.CustomerUpdateDTO;
import com.example.ecommerce.modules.customer.mapper.CustomerMapper;
import com.example.ecommerce.modules.customer.model.Customer;
import com.example.ecommerce.modules.customer.service.CustomerService;
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
        Customer customer = customerService.createCustomer(dto);
        CustomerListDTO customerCreateDTO = CustomerMapper.toCustomerListResponseDTO(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerCreateDTO);
    }

    @GetMapping
    public ResponseEntity<List<?>> getAllCustomers() {
        List<Customer> customers = customerService.findAll();
        List<CustomerListDTO> response = customers.stream()
                .map(CustomerMapper::toCustomerListResponseDTO)
                .toList();

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{idCustomer}")
    public ResponseEntity<CustomerListDTO> getIdUser(@Positive(message = "O ID do Customer tem que ser maior que 0") @PathVariable Integer idCustomer){
        return ResponseEntity.status(HttpStatus.OK).body((customerService.findCustomerById(idCustomer)));
    }

    @PatchMapping("/{idCustomer}")
    public ResponseEntity<CustomerUpdateDTO>  updateCustomer(@Positive(message = "O ID do Customer tem que ser maior que 0") @PathVariable Integer idCustomer, @Valid @RequestBody CustomerUpdateDTO dto) {
        Customer customer = customerService.updateCustomerById(idCustomer, dto);
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
