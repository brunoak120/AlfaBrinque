package com.book.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.book.app.util.UtilitarioUI;

@SuppressLint("Registered")
public class LoginUsuario extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logar);

        UtilitarioUI.hideSystemUI(getWindow());
    }

}
