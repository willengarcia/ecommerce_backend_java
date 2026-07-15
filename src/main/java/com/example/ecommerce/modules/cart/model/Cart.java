package com.example.ecommerce.modules.cart.model;

import com.example.ecommerce.modules.customers.model.Customers;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CartEnum status;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;
    private BigDecimal valorTotal;

    @OneToMany(mappedBy= "carro", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customers usuario;

    public Cart(Long id, CartEnum status, LocalDate data_criacao, LocalDate data_atualizacao, Customers usuario, BigDecimal valorTotal) {
        this.id = id;
        this.status = status;
        this.dataCriacao = data_criacao;
        this.dataAtualizacao = data_atualizacao;
        this.usuario = usuario;
        this.valorTotal = valorTotal;
    }

    public Cart() {}
}
