package com.example.ecommerce.modules.cart.model;

import com.example.ecommerce.modules.customers.model.Customers;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;

    @OneToMany(mappedBy= "carro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> items = new ArrayList<CartItem>();

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customers usuario;

    public Cart(Long id, boolean status, LocalDate data_criacao, LocalDate data_atualizacao, Customers usuario) {
        this.id = id;
        this.status = status;
        this.dataCriacao = data_criacao;
        this.dataAtualizacao = data_atualizacao;
        this.usuario = usuario;
    }

    public Cart() {}

    public Customers getUsuario() {
        return usuario;
    }

    public void setUsuario(Customers usuario) {
        this.usuario = usuario;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        items.forEach(item -> item.setCarro(this));
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate data_criacao) {
        this.dataCriacao = data_criacao;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate data_atualizacao) {
        this.dataAtualizacao = data_atualizacao;
    }
}
