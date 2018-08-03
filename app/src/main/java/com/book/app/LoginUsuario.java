package com.book.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.book.app.api_service.AlfabrinqueService;
import com.book.app.util.UtilitarioUI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("Registered")
public class LoginUsuario extends AppCompatActivity{

    Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl(AlfabrinqueService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    AlfabrinqueService service = retrofit.create(AlfabrinqueService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logar);

        UtilitarioUI.hideSystemUI(getWindow());
    }

}
