package com.book.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.book.app.api_pojo.Palavra;
import com.book.app.api_pojo.Usuario;
import com.book.app.api_service.AlfabrinqueService;
import com.book.app.util.UtilitarioUI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResponderQuizOnline extends AppCompatActivity {

    Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl(AlfabrinqueService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    AlfabrinqueService service = retrofit.create(AlfabrinqueService.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responder_quiz);

        UtilitarioUI.hideSystemUI(getWindow());

        buscarPalavra();

    }

    public void buscarPalavra() {
        Call<Palavra> call = service.buscarPalavra(Token.getInstance().getToken());

        call.enqueue(new Callback<Palavra>() {
            @Override
            public void onResponse(Call<Palavra> call, Response<Palavra> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ResponderQuizOnline.this, response.body().getNome(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ResponderQuizOnline.this, "Não foi possivel encontrar Palavra :(", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Palavra> call, Throwable t) {
                Toast.makeText(ResponderQuizOnline.this, "Algum erro inesperado aconteceu :(", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void enviarPalavra() {

    }
}
