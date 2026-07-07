package com.example.ecommerce.modules.cart.model;

import com.example.ecommerce.modules.product.model.Product;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cart_product",
                        columnNames = {"carro_id", "product_id"}
                )
        }
)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private float precoUnitario;
    private float subtotal;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;

    @ManyToOne
    private Cart carro;

    @ManyToOne
    private Product product;

    public CartItem(Long id, Integer quantidade, float precoUnitario, float subtotal) {
        this.id = id;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
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

    public float getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(float precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataAtualizacao = dataCriacao;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Cart getCarro() {
        return carro;
    }

    public void setCarro(Cart carro) {
        this.carro = carro;
    }
}
