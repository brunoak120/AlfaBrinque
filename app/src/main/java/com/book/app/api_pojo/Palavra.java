package com.book.app.api_pojo;

import retrofit2.http.Url;

public class Palavra {
    private Integer id;
    private String nome;
    private Double peso;
    private String imagem;
    private String categoria;

    public Palavra() {

    }

    public Palavra(Integer id, String nome, Double peso, String imagem, String categoria) {
        this.id = id;
        this.nome = nome;
        this.peso = peso;
        this.imagem = imagem;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
