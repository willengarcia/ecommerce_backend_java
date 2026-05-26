package com.example.ecommerce.modules.cart.model;

import com.example.ecommerce.modules.customers.model.Customers;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status;
    private Date data_criacao;
    private Date data_atualizacao;

    @OneToMany(mappedBy= "carro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> items = new ArrayList<CartItem>();

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customers usuario;

    public Cart(Long id, boolean status, Date data_criacao, Date data_atualizacao) {
        this.id = id;
        this.status = status;
        this.data_criacao = data_criacao;
        this.data_atualizacao = data_atualizacao;
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

    public Date getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }

    public Date getData_atualizacao() {
        return data_atualizacao;
    }

    public void setData_atualizacao(Date data_atualizacao) {
        this.data_atualizacao = data_atualizacao;
    }
}
