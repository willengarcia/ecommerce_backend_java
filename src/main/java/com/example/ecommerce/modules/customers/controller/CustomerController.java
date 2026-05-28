package com.example.ecommerce.modules.customers.controller;

import com.example.ecommerce.modules.customers.dto.CustomerDTO;
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
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO dto) {
        try{
            Customers customer = customerService.criarUsuario(dto);
            CustomerDTO customerDTO = new CustomerDTO(
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
            return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Customers>> getAllCustomers() {
        List<Customers> customer = customerService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
    
    @GetMapping("/{idCustomer}")
    public CustomerDTO getIdUser(@PathVariable Integer idCustomer){
        return customerService.buscarUsuarioPorId(idCustomer);
    }

    @PutMapping("/{idCustomer}")
    public ResponseEntity<CustomerDTO>  updateCustomer(@PathVariable Integer idCustomer, @RequestBody CustomerDTO dto) {
        Customers customer = customerService.atualizarUsuarioPorId(idCustomer, dto);
        CustomerDTO customerDTO = new CustomerDTO(
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
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

}
