package com.book.app.api_pojo;

public class RespostaEnviar {
    private boolean resposta;

    public RespostaEnviar() {
    }

    public RespostaEnviar(boolean resposta) {
        this.resposta = resposta;
    }

    public boolean isResposta() {
        return resposta;
    }

    public void setResposta(boolean resposta) {
        this.resposta = resposta;
    }
}
