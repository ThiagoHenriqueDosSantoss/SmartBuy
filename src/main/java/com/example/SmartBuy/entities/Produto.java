package com.example.SmartBuy.entities;

public class Produto {
    private Long id;
    private String titulo;
    private Double preco;
    private String descricao;
    private String categoria;
    private String imagem;
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
