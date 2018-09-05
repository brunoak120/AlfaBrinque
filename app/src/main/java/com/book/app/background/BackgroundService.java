package com.book.app.background;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.book.app.Token;
import com.book.app.api_pojo.Palavra;
import com.book.app.api_service.AlfabrinqueService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("Registered")
public class BackgroundService extends IntentService {

    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(AlfabrinqueService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AlfabrinqueService service = retrofit.create(AlfabrinqueService.class);

        Call<Palavra> call = service.buscarPalavra(Token.getInstance().getToken());

        try {
            Palavra resultado = call.execute().body();
            Log.i("Retrofit", "Sucesso");
        } catch (IOException e) {
            Log.i("Retrofit", e.getMessage());
        }
    }
}
