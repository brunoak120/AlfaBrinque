package com.book.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.book.app.pojo.Som;
import com.book.app.data.AppDAO;
import com.book.app.util.UtilitarioUI;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UtilitarioUI.hideSystemUI(getWindow());

        AppDAO.newInstance(this);

    }

    public void iniciarJogo(View view) {
        Intent intent = new Intent(this, EscolherUsuario.class);

        startActivity(intent);

        Som.bip(this);
    }

    public void fazerLogin(View view) {
        Intent intent = new Intent(this, LoginUsuario.class);

        Som.bip(this);

        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_sair_title))
                .setMessage(getString(R.string.dialog_sair_texto))
                .setIcon(R.drawable.ic_close_black_24dp_dialog)
                .setPositiveButton(getString(R.string.txt_dialog_sim), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.txt_cancelar), null).show();
    }


}
