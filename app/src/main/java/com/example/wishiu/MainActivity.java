package com.example.wishiu;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    BD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bd = new BD(this);
        chamarBD();
        verificarUtilizador();
    }

    public void chamarBD(){
        bd.getWritableDatabase();
    }

    public void verificarUtilizador(){
        Cursor utl = bd.getNomeUtilizador();
        if(utl != null && utl.moveToFirst() ){
            if(utl.getString(0) == "default"){
                skipToBemvindo();
                utl.close();
            } else {
                skipToLoading(utl.getString(0));
                utl.close();
            }
        } else {
            skipToBemvindo();
        }
    }

    public void skipToBemvindo(){
        Intent bemvindo = new Intent(MainActivity.this, BemvindoActivity.class);
        startActivity(bemvindo);
        finish();
    }

    public void skipToLoading(String nome){
        Intent loading = new Intent(this, LoadingActivity.class);
        loading.putExtra("utilizador", nome);
        startActivity(loading);
        finish();
    }
}