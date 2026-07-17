package com.example.ecommerce.modules.customers.model;

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
public class Customers {
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
    @OneToMany(mappedBy= "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Address> enderecos =  new ArrayList<>();
    @OneToMany(mappedBy= "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Order> orders =  new ArrayList<>();
    @OneToMany(mappedBy = "usuario")
    private List<Cart> carts = new ArrayList<>();

    public Customers() {}

    public Customers (Integer id, String nome_completo, String cpf, String email, String telefone, String senhaHash, CustomerEnum status) {
        this.id = id;
        this.nomeCompleto = nome_completo;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senhaHash = senhaHash;
        this.status = status;
    }

    public void setEnderecos(List<Address> enderecos) {
        enderecos.forEach(address -> address.setUsuario(this));
        this.enderecos = enderecos;
    }

    public CustomerEnum isStatus() { // Remover método
        return status;
    }

}
