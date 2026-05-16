package com.example.ecommerce.model.cart;

import com.example.ecommerce.model.product.Product;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private float preco_unitario;
    private float subtotal;
    private Date data_criacao;
    private Date data_atualizacao;

    @ManyToOne
    private Cart carro;

    @ManyToOne
    private Product product;

    public CartItem(Long id, Integer quantidade, float preco_unitario, float subtotal, Date data_criacao, Date data_atualizacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.preco_unitario = preco_unitario;
        this.subtotal = subtotal;
        this.data_criacao = data_criacao;
        this.data_atualizacao = data_atualizacao;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartItem() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public float getPreco_unitario() {
        return preco_unitario;
    }

    public void setPreco_unitario(float preco_unitario) {
        this.preco_unitario = preco_unitario;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
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

    public Cart getCarro() {
        return carro;
    }

    public void setCarro(Cart carro) {
        this.carro = carro;
    }
}
