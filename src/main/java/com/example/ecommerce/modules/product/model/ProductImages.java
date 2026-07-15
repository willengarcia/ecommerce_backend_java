package com.example.ecommerce.modules.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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

    public ProductImages() {}

    public boolean getImagemPrincipal() {
        return imagemPrincipal;
    }

}
