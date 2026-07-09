package com.example.ecommerce.modules.product.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
@Entity
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String urlImagem;
    private String nomeImagem;
    private String textoAlternativo;
    private boolean imagemPrincipal;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;

    @ManyToOne
    private Product product;

    public ProductImages(Long id, String urlImagem, String textoAlternativo, boolean imagemPrincipal, Product product) {
        this.id = id;
        this.urlImagem = urlImagem;
        this.textoAlternativo = textoAlternativo;
        this.imagemPrincipal = imagemPrincipal;
        this.dataCriacao = LocalDate.now();
        this.dataAtualizacao = LocalDate.now();
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductImages() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public String getTextoAlternativo() {
        return textoAlternativo;
    }

    public void setTextoAlternativo(String textoAlternativo) {
        this.textoAlternativo = textoAlternativo;
    }

    public boolean getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(boolean imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
