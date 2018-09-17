package com.book.app;

import java.util.List;

public class Errors {
    private List<String> password;
    private List<String> senha;
    private List<String> email;
    private List<String> error;
    private List<String> confirmarSenha;

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public List<String> getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(List<String> confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public List<String> getSenha() {
        return senha;
    }

    public void setSenha(List<String> senha) {
        this.senha = senha;
    }
}
