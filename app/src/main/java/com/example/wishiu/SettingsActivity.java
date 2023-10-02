package com.example.wishiu;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    BD bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        bd = new BD(this);
        final ImageButton goback = (ImageButton) findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Button confirmar = (Button) findViewById(R.id.continuarsettingsButton);
        final EditText settingsName = (EditText) findViewById(R.id.settingsName);
        Cursor nomest = bd.getNomeUtilizador();
        if(nomest != null && nomest.moveToFirst()){
            settingsName.setText(nomest.getString(0));
        }
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeatualizado = settingsName.getText().toString().trim();
                boolean nomeat = bd.inserirNome(nomeatualizado);
                if(nomeat == true){
                   abrirAlerta("Your settings have been updated.");
                } else {
                    abrirAlerta("An error occured.");
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
}
