package com.book.app;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.book.app.pojo.Som;
import com.book.app.data.AppDAO;
import com.book.app.util.UtilitarioUI;

import me.drakeet.materialdialog.MaterialDialog;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnIniciar;

    public static final int REQUEST_PERMISSIONS_CODE = 128;

    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UtilitarioUI.hideSystemUI(getWindow());

        AppDAO.newInstance(this);

        requererPermissao();

        if (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&  ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                callDialog( "É preciso a permissão READ_EXTERNAL_STORAGE para salvar dados do jogador no modo offline.", new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} );
            } else {
                ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_CODE );
            }
        }

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

    public void requererPermissao() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, 1);
    }

    private void callDialog( String message, final String[] permissions ){
        mMaterialDialog = new MaterialDialog(this)
                .setTitle("Permission")
                .setMessage( message )
                .setPositiveButton("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_PERMISSIONS_CODE);
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

}
