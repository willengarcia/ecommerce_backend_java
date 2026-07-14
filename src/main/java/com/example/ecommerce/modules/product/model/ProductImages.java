package com.example.ecommerce.modules.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Entity
public class ProductImages {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    private String urlImagem;
    @Setter
    @Getter
    private String nomeImagem;
    private String textoAlternativo;
    @Setter
    private boolean imagemPrincipal;
    @Setter
    @Getter
    private LocalDate dataCriacao;
    @Setter
    @Getter
    private LocalDate dataAtualizacao;

    @Setter
    @Getter
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
