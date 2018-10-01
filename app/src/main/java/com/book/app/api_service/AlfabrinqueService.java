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
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AlfabrinqueService {
    /**
     * @description production variables
     */
    public static final String BASE_URL = "http://72.14.185.116/api/";
    public static final String BASE_URL_IMAGEM = "http://72.14.185.116";

    /*public static final String BASE_URL = "http://192.168.15.14:8000/api/";
    public static final String BASE_URL_IMAGEM = "http://192.168.15.14:8000";*/

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("usuario/registrar")
    Call<Usuario> registrar(@Body CadastrarUsuario cadastrarUsuario);

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("usuario/login")
    Call<Usuario> login(@Body Login login);

    @GET("buscarPalavra")
    Call<Palavra> buscarPalavra(@Header("Authorization") String token);

    @POST("enviarPalavra")
    Call<RespostaEnviar> enviarPalavra(@Header("Authorization") String token, @Body PalavraEnviar palavra);

}
