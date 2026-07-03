package com.example.ecommerce.modules.product.model;

import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.category.model.Category;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String slug;
    @Column(name = "descricao_curta")
    private String descricaoCurta;
    private String descricao;
    private float preco;
    @Column(name = "preco_promocional")
    private float precoPromocional;
    @Column(name = "quantidadeEstoque")
    private Integer quantidadeEstoque;
    @Column(name = "quantidade_reservada")
    private Integer quantidadeReservada;
    @Column(name = "estoqueMinimo")
    private Integer estoqueMinimo;
    @Column(unique = true)
    private String sku;
    private float peso;
    private float altura;
    private float largura;
    private float comprimento;
    @Column(name = "media_avaliacao")
    private float mediaAvaliacao;
    @Column(name = "total_avaliacao")
    private Integer totalAvaliacoes;
    @Enumerated(EnumType.STRING)
    private ProductEnum status;

    private LocalDate dataCriacao;

    private LocalDate dataCtualizacao;

    @OneToMany(mappedBy= "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImages> imagem =  new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy= "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> items = new ArrayList<>();


    public Product(Long id, String nome, String slug, String descricao_curta, String descricao, float preco, float preco_promocional, Integer quantidade_estoque, Integer quantidade_reservada, Integer estoque_minimo, String sku, float peso, float altura, float largura, float comprimento, float media_avaliacao, Integer total_avaliacoes, ProductEnum status) {
        this.id = id;
        this.nome = nome;
        this.slug = slug;
        this.descricaoCurta = descricao_curta;
        this.descricao = descricao;
        this.preco = preco;
        this.precoPromocional = preco_promocional;
        this.quantidadeEstoque = quantidade_estoque;
        this.quantidadeReservada = quantidade_reservada;
        this.estoqueMinimo = estoque_minimo;
        this.sku = sku;
        this.peso = peso;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.mediaAvaliacao = media_avaliacao;
        this.totalAvaliacoes = total_avaliacoes;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductEnum isStatus() {
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

    public ProductEnum getStatus() {
        return status;
    }

    public void setStatus(ProductEnum status) {
        this.status = status;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public void setDescricaoCurta(String descricaoCurta) {
        this.descricaoCurta = descricaoCurta;
    }

    public float getPrecoPromocional() {
        return precoPromocional;
    }

    public void setPrecoPromocional(float precoPromocional) {
        this.precoPromocional = precoPromocional;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Integer getQuantidadeReservada() {
        return quantidadeReservada;
    }

    public void setQuantidadeReservada(Integer quantidadeReservada) {
        this.quantidadeReservada = quantidadeReservada;
    }

    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public float getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public void setMediaAvaliacao(float mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }

    public Integer getTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public void setTotalAvaliacoes(Integer totalAvaliacoes) {
        this.totalAvaliacoes = totalAvaliacoes;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataCtualizacao() {
        return dataCtualizacao;
    }

    public void setDataCtualizacao(LocalDate dataCtualizacao) {
        this.dataCtualizacao = dataCtualizacao;
    }
}
