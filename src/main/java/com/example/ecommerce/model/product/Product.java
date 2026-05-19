package com.example.ecommerce.model.product;

import com.example.ecommerce.model.cart.CartItem;
import com.example.ecommerce.model.category.Category;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String slug;
    private String descricao_curta;
    private String descricao;
    private float preco;
    private float preco_promocional;
    private Integer quantidade_estoque;
    private Integer quantidade_reservada;
    private Integer estoque_minimo;
    private String sku;
    private float peso;
    private float altura;
    private float largura;
    private float comprimento;
    private float media_avaliacao;
    private Integer total_avaliacoes;
    private boolean status;
    private LocalDate data_criacao;
    private LocalDate data_atualizacao;

    @OneToMany(mappedBy= "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductImages> imagem =  new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoria;

    @OneToMany(mappedBy= "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> items = new ArrayList<>();


    public Product(Long id, String nome, String slug, String descricao_curta, String descricao, float preco, float preco_promocional, Integer quantidade_estoque, Integer quantidade_reservada, Integer estoque_minimo, String sku, float peso, float altura, float largura, float comprimento, float media_avaliacao, Integer total_avaliacoes, boolean status) {
        this.id = id;
        this.nome = nome;
        this.slug = slug;
        this.descricao_curta = descricao_curta;
        this.descricao = descricao;
        this.preco = preco;
        this.preco_promocional = preco_promocional;
        this.quantidade_estoque = quantidade_estoque;
        this.quantidade_reservada = quantidade_reservada;
        this.estoque_minimo = estoque_minimo;
        this.sku = sku;
        this.peso = peso;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.media_avaliacao = media_avaliacao;
        this.total_avaliacoes = total_avaliacoes;
        this.status = status;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        items.forEach(itens -> itens.setProduct(this));
        this.items = items;
    }


    public List<ProductImages> getImagem() {
        return imagem;
    }

    public void setImagem(List<ProductImages> imagem) {
        imagem.forEach(image -> image.setProduct(this));
        this.imagem = imagem;
    }

    public Category getCategoria() {
        return categoria;
    }

    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }

    public Product() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescricao_curta() {
        return descricao_curta;
    }

    public void setDescricao_curta(String descricao_curta) {
        this.descricao_curta = descricao_curta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public float getPreco_promocional() {
        return preco_promocional;
    }

    public void setPreco_promocional(float preco_promocional) {
        this.preco_promocional = preco_promocional;
    }

    public Integer getQuantidade_estoque() {
        return quantidade_estoque;
    }

    public void setQuantidade_estoque(Integer quantidade_estoque) {
        this.quantidade_estoque = quantidade_estoque;
    }

    public Integer getQuantidade_reservada() {
        return quantidade_reservada;
    }

    public void setQuantidade_reservada(Integer quantidade_reservada) {
        this.quantidade_reservada = quantidade_reservada;
    }

    public Integer getEstoque_minimo() {
        return estoque_minimo;
    }

    public void setEstoque_minimo(Integer estoque_minimo) {
        this.estoque_minimo = estoque_minimo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getLargura() {
        return largura;
    }

    public void setLargura(float largura) {
        this.largura = largura;
    }

    public float getComprimento() {
        return comprimento;
    }

    public void setComprimento(float comprimento) {
        this.comprimento = comprimento;
    }

    public Integer getTotal_avaliacoes() {
        return total_avaliacoes;
    }

    public void setTotal_avaliacoes(Integer total_avaliacoes) {
        this.total_avaliacoes = total_avaliacoes;
    }

    public float getMedia_avaliacao() {
        return media_avaliacao;
    }

    public void setMedia_avaliacao(float media_avaliacao) {
        this.media_avaliacao = media_avaliacao;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDate data_criacao) {
        this.data_criacao = data_criacao;
    }

    public LocalDate getData_atualizacao() {
        return data_atualizacao;
    }

    public void setData_atualizacao(LocalDate data_atualizacao) {
        this.data_atualizacao = data_atualizacao;
    }
}
