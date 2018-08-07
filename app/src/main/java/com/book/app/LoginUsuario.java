package com.book.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.book.app.api_pojo.Login;
import com.book.app.pojo.Som;
import com.book.app.api_pojo.Usuario;
import com.book.app.api_service.AlfabrinqueService;
import com.book.app.util.UtilitarioUI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("Registered")
public class LoginUsuario extends AppCompatActivity {
    private static String token;

    TextView email;
    TextView password;

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

    public void logar(View view) {
        Som.bip(this);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);

        Login login = new Login(email.getText().toString(), password.getText().toString());
        Call<Usuario> call = service.login(login);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginUsuario.this, response.body().getToken(), Toast.LENGTH_LONG).show();
                    token = response.body().getToken();
                } else {
                    Toast.makeText(LoginUsuario.this, "Login errado :(", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(LoginUsuario.this, "Erro :(", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void criarConta(View view){
        Intent intent = new Intent(this, CriarContaUsuario.class);

        startActivity(intent);

        Som.bip(this);
    }

}
