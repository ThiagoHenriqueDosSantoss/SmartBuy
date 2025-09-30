package com.example.SmartBuy.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "smb_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproduto", updatable = false, nullable = false)
    private Long id;

    @Column(name = "titulo", updatable = false, nullable = false)
    private String titulo;

    @Column(name = "preco", updatable = false, nullable = false)
    private Double preco;

    @Column(name = "descricao", updatable = false, nullable = true)
    private String descricao;

    @Column(name = "categoria", updatable = false, nullable = false)
    private String categoria;

    @Column(name = "imagem", updatable = false, nullable = false)
    private String imagem;

    @Column(name = "linkPermanente", updatable = false, nullable = true)
    private String linkPermanente;
    private com.example.SmartBuy.dto.Produto.ProdutoDTO.RatingDTO avaliacao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getLinkPermanente() {
        return linkPermanente;
    }

    public void setLinkPermanente(String linkPermanente) {
        this.linkPermanente = linkPermanente;
    }

    public com.example.SmartBuy.dto.Produto.ProdutoDTO.RatingDTO getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(com.example.SmartBuy.dto.Produto.ProdutoDTO.RatingDTO avaliacao) {
        this.avaliacao = avaliacao;
    }

    // Classe interna para Avaliação (Rating)
    public static class AvaliacaoDTO {
        private Double nota;
        private Integer quantidade;

        public Double getNota() {
            return nota;
        }

        public void setNota(Double nota) {
            this.nota = nota;
        }

        public Integer getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }
    }
}
