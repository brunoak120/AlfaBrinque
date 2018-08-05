package com.book.app;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.book.app.pojo.Som;
import com.book.app.util.UtilitarioUI;

@SuppressLint("Registered")
public class CriarContaUsuario extends AppCompatActivity {

    private EditText nome;
    private EditText senha;
    private EditText confirmarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        UtilitarioUI.hideSystemUI(getWindow());

    }

    public void registrar(View view) {
        nome = (EditText) findViewById(R.id.input_nome);
        senha = (EditText) findViewById(R.id.input_senha);
        confirmarSenha = (EditText) findViewById(R.id.input_confirmar_senha);

        Som.bip(this);
    }

}
