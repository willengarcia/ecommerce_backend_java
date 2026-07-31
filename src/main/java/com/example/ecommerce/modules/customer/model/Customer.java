package com.example.ecommerce.modules.customer.model;

import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.order.model.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeCompleto;
    @Column(unique = true)
    private String cpf;
    @Column(unique = true)
    private String email;
    private String telefone;
    private String senhaHash;
    @Enumerated(EnumType.STRING)
    private CustomerEnum status;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;
    @OneToMany(mappedBy= "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> enderecos =  new ArrayList<>();
    @OneToMany(mappedBy= "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders =  new ArrayList<>();
    @OneToMany(mappedBy = "customer")
    private List<Cart> carts = new ArrayList<>();

    public Customer() {}

    public Customer(String nome_completo, String cpf, String email, String telefone, String senhaHash) {
        this.nomeCompleto = nome_completo;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senhaHash = senhaHash;
        this.status = CustomerEnum.ATIVO;
        this.dataAtualizacao = LocalDate.now();
        this.dataCriacao = LocalDate.now();
    }

}
