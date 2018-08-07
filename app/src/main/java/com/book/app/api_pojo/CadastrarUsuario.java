package com.book.app.api_pojo;

public class CadastrarUsuario {
    private String nome;
    private String email;
    private String senha;
    private String bairro;
    private String logradouro;
    private String cidade;
    private String estado;

    public CadastrarUsuario() {

    }

    public CadastrarUsuario(String nome, String email, String senha, String bairro, String logradouro, String cidade, String estado) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
