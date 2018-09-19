package com.book.app;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.book.app.api_pojo.CadastrarUsuario;
import com.book.app.api_pojo.Login;
import com.book.app.api_pojo.Usuario;
import com.book.app.api_service.AlfabrinqueService;
import com.book.app.pojo.Som;
import com.book.app.util.UtilitarioUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

@SuppressLint("Registered")
public class CriarContaUsuario extends AppCompatActivity {
    private static String token;

    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText confirmarSenha;

    private double latitude;
    private double longitude;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;

    private FusedLocationProviderClient client;
    private Address endereco;

    Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl(AlfabrinqueService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    AlfabrinqueService service = retrofit.create(AlfabrinqueService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        UtilitarioUI.hideSystemUI(getWindow());

        requererPermissao();

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    try {
                        endereco = buscarEndereco(latitude, longitude);

                        logradouro = endereco.getFeatureName();
                        bairro = endereco.getSubLocality();
                        cidade = endereco.getSubAdminArea();
                        estado = endereco.getAdminArea();

                    } catch (IOException e) {
                        Log.i("GPS", e.getMessage());
                    }
                }
            }
        });
    }

    public void requererPermissao() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    public void registrar(View view) {
        nome = (EditText) findViewById(R.id.input_nome);
        email = (EditText) findViewById(R.id.input_email);
        senha = (EditText) findViewById(R.id.input_senha);
        confirmarSenha = (EditText) findViewById(R.id.input_confirmar_senha);

        CadastrarUsuario cadastrarUsuario = new CadastrarUsuario(
                nome.getText().toString(),
                email.getText().toString(),
                senha.getText().toString(),
                confirmarSenha.getText().toString(),
                bairro,
                logradouro,
                cidade,
                estado
        );

        Call<Usuario> call = service.registrar(cadastrarUsuario);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    token = response.body().getToken();
                    Token.getInstance().setToken(token);
                    iniciarJogoOnline();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        imprimeErros(jObjError.getString("errors"));
                    } catch (Exception e) {
                        Toast.makeText(CriarContaUsuario.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(CriarContaUsuario.this, "Erro :(", Toast.LENGTH_LONG).show();
            }
        });

        Som.bip(this);
    }

    public Address buscarEndereco(double latitude, double longitude) throws IOException{
        Geocoder geocoder;
        Address endereco = null;
        List<Address> enderecos;

        geocoder = new Geocoder(getApplicationContext());

        enderecos = geocoder.getFromLocation(latitude, longitude, 1);

        if (enderecos.size() > 0) {
            endereco = enderecos.get(0);
        }

        return endereco;
    }

    public void iniciarJogoOnline() {
        Intent intent = new Intent(this, ResponderQuizOnline.class);

        startActivity(intent);

        Som.bip(this);
    }

    public void imprimeErros(String json){
        String errosImprimir = "";
        Gson gsonErrors = new Gson();
        Errors errors = gsonErrors.fromJson(json, Errors.class);

        if (errors.getEmail() != null) {
            for(String message: errors.getEmail()){
                errosImprimir = errosImprimir + message + "\n";
            }
        }

        if (errors.getPassword() != null) {
            for(String message: errors.getPassword()){
                errosImprimir = errosImprimir + message + "\n";
            }
        }

        if (errors.getError() != null) {
            for(String message: errors.getError()){
                errosImprimir = errosImprimir + message + "\n";
            }
        }

        if (errors.getSenha() != null) {
            for(String message: errors.getSenha()){
                errosImprimir = errosImprimir + message + "\n";
            }
        }

        if (errors.getConfirmarSenha() != null) {
            for(String message: errors.getConfirmarSenha()){
                errosImprimir = errosImprimir + message + "\n";
            }
        }

        Toast.makeText(CriarContaUsuario.this, errosImprimir, Toast.LENGTH_LONG).show();
    }
}
