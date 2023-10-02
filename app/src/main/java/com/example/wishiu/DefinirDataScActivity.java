package com.example.wishiu;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

public class DefinirDataScActivity extends AppCompatActivity {
    BD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_data);
        Intent anterior = getIntent();
        final String idData = anterior.getStringExtra("idItem");
        bd = new BD(this);
        final ImageButton gobackdata = (ImageButton) findViewById(R.id.gobackdata);
        final ImageButton lixodata = (ImageButton) findViewById(R.id.lixodata);
        final DatePicker myDatePicker = (DatePicker) findViewById(R.id.date_value);
        final Button confimarData = (Button) findViewById(R.id.continuardataButton);
        myDatePicker.setCalendarViewShown(false);
        gobackdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Intent voltarAoPrincipal = new Intent(DefinirDataScActivity.this, PrincipalActivity.class);
        lixodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int removido = bd.deleteScheduled(idData);
                if(removido > 0){
                    startActivity(voltarAoPrincipal);
                    finish();
                }
            }
        });
        Cursor atualData = bd.getDataOfAchieved(idData);
        if(atualData != null && atualData.moveToFirst()){
            String dataStr = atualData.getString(0);
            String[] datas = dataStr.split("-");
            int ano = Integer.parseInt(datas[0]);
            int mes = Integer.parseInt(datas[1]);
            int dia = Integer.parseInt(datas[2]);
            myDatePicker.updateDate(ano,mes,dia);
            confimarData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dataEscolhida = myDatePicker.getYear()+"-"+myDatePicker.getMonth()+"-"+myDatePicker.getDayOfMonth();
                    boolean atualizadaData = bd.updateData(idData, dataEscolhida);
                    if(atualizadaData == true){
                        startActivity(voltarAoPrincipal);
                        finish();
                    }
                }
            });
        } else {
            finish();
        }
        atualData.close();
    }
}
