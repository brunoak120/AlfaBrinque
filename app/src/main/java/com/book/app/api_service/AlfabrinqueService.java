package com.book.app.api_service;

import com.book.app.api_pojo.Login;
import com.book.app.api_pojo.Palavra;
import com.book.app.api_pojo.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AlfabrinqueService {
    public static final String BASE_URL = "http://localhost:8000/api/";

    @POST("registrar")
    boolean registrar();

    @POST("login")
    Call<Usuario> login(@Body Login login);

    @GET("buscarPalavra")
    Call<Palavra> buscarPalavra(@Header("Authorization") String token);

    @POST("enviarPalavra")
    boolean enviarPalavra();

}
