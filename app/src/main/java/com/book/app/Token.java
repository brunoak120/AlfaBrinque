package com.book.app;

public class Token {
    private static Token objeto = null;

    private String token;

    protected Token(){}

    public static synchronized Token getInstance() {
        if(null == objeto){
            objeto = new Token();
        }
        return objeto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = "Bearer " + token;
    }
}
