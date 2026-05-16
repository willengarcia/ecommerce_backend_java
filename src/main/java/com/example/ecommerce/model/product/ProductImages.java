package com.example.ecommerce.model.product;

import jakarta.persistence.*;

import java.util.Date;
@Entity
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url_imagem;
    private String texto_alternativo;
    private String ordem;
    private String imagem_principal;
    private Date data_criacao;

    @ManyToOne
    private Product product;

    public ProductImages(Long id, String url_imagem, String texto_alternativo, String ordem, String imagem_principal) {
        this.id = id;
        this.url_imagem = url_imagem;
        this.texto_alternativo = texto_alternativo;
        this.ordem = ordem;
        this.imagem_principal = imagem_principal;
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

    public String getUrl_imagem() {
        return url_imagem;
    }

    public void setUrl_imagem(String url_imagem) {
        this.url_imagem = url_imagem;
    }

    public String getTexto_alternativo() {
        return texto_alternativo;
    }

    public void setTexto_alternativo(String texto_alternativo) {
        this.texto_alternativo = texto_alternativo;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getImagem_principal() {
        return imagem_principal;
    }

    public void setImagem_principal(String imagem_principal) {
        this.imagem_principal = imagem_principal;
    }

    public Date getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }
}
