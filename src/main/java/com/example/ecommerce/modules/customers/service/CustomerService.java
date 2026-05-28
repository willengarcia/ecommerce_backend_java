package com.example.ecommerce.modules.customers.service;

import com.example.ecommerce.modules.customers.dto.CustomerDTO;
import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.customers.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public List<Customers> findAll(){
        return customerRepository.findAll();
    }
    public Customers criarUsuario(CustomerDTO customerDTO){
        Customers customers = new Customers();
        if(customerDTO.nomeCompleto().isEmpty() || customerDTO.cpf().isEmpty() || customerDTO.email().isEmpty() || customerDTO.telefone().isEmpty() || customerDTO.senhaHash().isEmpty()){
            throw new RuntimeException("É obrigatório informar os campos: Nome, Email, Telefone, Senha, CPF");
        } else if (customerDTO.cpf().length() != 11) {
            throw new RuntimeException("É necessário informar 11 caracteres");
        } if (customerRepository.existsByCpf(customerDTO.cpf())) {
            throw new RuntimeException("CPF já cadastrado");
        } if (customerRepository.existsByEmail(customerDTO.email())) {
            throw new RuntimeException("Email já cadastrado");
        } else {
            customers.setCpf(customerDTO.cpf());
            customers.setNomeCompleto(customerDTO.nomeCompleto());
            customers.setEmail(customerDTO.email());
            customers.setTelefone(customerDTO.telefone());
            customers.setStatus("ATIVO");
            customers.setSenhaHash(customerDTO.senhaHash());
            customers.setDataCriacao(LocalDate.now());
            customers.setDataAtualizacao(LocalDate.now());
            return customerRepository.save(customers);
        }
    }
    public Customers atualizarUsuarioPorId(Integer id, CustomerDTO customerDTO){
        Customers customers = customerRepository.findById(id).orElseThrow();
        if(customerDTO.nomeCompleto().isEmpty() || customerDTO.email().isEmpty() || customerDTO.telefone().isEmpty() || customerDTO.senhaHash().isEmpty()){
            throw new RuntimeException("É obrigatório informar os campos: Nome, Email, Telefone, Senha");
        } else{

            customers.setNomeCompleto(customerDTO.nomeCompleto());
            customers.setEmail(customerDTO.email());
            customers.setTelefone(customerDTO.telefone());
            customers.setSenhaHash(customerDTO.senhaHash());
            customers.setDataAtualizacao(LocalDate.now());

            return customerRepository.save(customers);
        }
    }
}
