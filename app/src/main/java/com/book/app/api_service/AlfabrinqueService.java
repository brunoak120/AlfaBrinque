package com.book.app.api_service;

import com.book.app.api_pojo.CadastrarUsuario;
import com.book.app.api_pojo.Login;
import com.book.app.api_pojo.Palavra;
import com.book.app.api_pojo.Usuario;
import com.book.app.api_pojo.PalavraEnviar;
import com.book.app.api_pojo.RespostaEnviar;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AlfabrinqueService {
    public static final String BASE_URL = "http://192.168.0.102:8000/api/";

    @POST("usuario/registrar")
    Call<Usuario> registrar(@Body CadastrarUsuario cadastrarUsuario);

    @POST("usuario/login")
    Call<Usuario> login(@Body Login login);

    @GET("buscarPalavra")
    Call<Palavra> buscarPalavra(@Header("Authorization") String token);

    @POST("enviarPalavra")
    Call<RespostaEnviar> enviarPalavra(@Header("Authorization") String token, @Body PalavraEnviar palavra);

}
