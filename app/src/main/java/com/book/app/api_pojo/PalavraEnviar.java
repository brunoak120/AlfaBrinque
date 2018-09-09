package com.book.app.api_pojo;

public class PalavraEnviar {
    private int id_palavra;
    private int tempo;
    private String palavra;

    public PalavraEnviar() {

    }

    public PalavraEnviar(int id_palavra, int tempo, String palavra) {
        this.id_palavra = id_palavra;
        this.tempo = tempo;
        this.palavra = palavra;
    }

    public int getId_palavra() {
        return id_palavra;
    }

    public void setId_palavra(int id_palavra) {
        this.id_palavra = id_palavra;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
}
