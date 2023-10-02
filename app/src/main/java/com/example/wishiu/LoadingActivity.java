package com.example.wishiu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Intent anterior = getIntent();
        final String nome_utilizador = anterior.getStringExtra("utilizador");
        final TextView nmu = (TextView) findViewById(R.id.nmu);
        nmu.setText(nome_utilizador);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, PrincipalActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1600);

    }
}
