package com.example.wishiu;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BemvindoActivity extends AppCompatActivity {
    BD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bemvindo);
        bd = new BD(this);

        final Button continuarButton = (Button) findViewById(R.id.continuarButton);
        final EditText nomeInput = (EditText) findViewById(R.id.nomeInput);
        continuarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorInput = nomeInput.getText().toString().trim();
                if(valorInput == "" || valorInput == " " || valorInput == null || valorInput.isEmpty()){
                    abrirAlerta("Please, insert your name.");
                } else {
                    inserirName(valorInput);
                }
            }
        });
    }

    public void abrirAlerta(String message){
        // criar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        // abrir
        AlertDialog bvdialog = builder.create();
        bvdialog.show();
    }

    public void inserirName(String valorN){
        boolean inseridoN = bd.inserirNome(valorN);
        if(inseridoN == true){
            //mudar de atividade
            Intent principal = new Intent(BemvindoActivity.this, PrincipalActivity.class);
            startActivity(principal);
            finish();
        } else {
            abrirAlerta("An error occured. Please, try again.");
        }
    }


}
