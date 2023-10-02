package com.example.wishiu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VerActivity extends AppCompatActivity {
    BD bd;
    RecyclerView savingsRV;
    LinearLayoutManager sllm;
    ArrayList<ProdutosSavings> savings;
    SavingsAdapter savingsA;
    float soma;
    float gastos;
    float bAtual;
    float atual;
    float fbP;
    int fP;
    ArrayList<String> frases;
    Date dataO;
    String google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);
        bd = new BD(this);
        soma = 0;
        gastos = 0;
        bAtual = 0;
        atual = 0;
        fbP = 0;
        fP = 0;
        Intent anterior = getIntent();
        final String idVer = anterior.getStringExtra("idItem");
        Cursor pi = bd.getProdutoInfo(idVer);
        final Button precoBox = (Button) findViewById(R.id.precoBox);
        final TextView verTitulo = (TextView) findViewById(R.id.verTitulo);
        final TextView verCategoria = (TextView) findViewById(R.id.verCategoria);
        final byte[] byteImagem;
        final ImageView verImagem = (ImageView) findViewById(R.id.verImagem);
        final TextView progessoValor = (TextView) findViewById(R.id.progressoValor);

        if(pi != null && pi.moveToFirst()){
            precoBox.setText("$"+pi.getString(2));
            bAtual = Float.parseFloat(pi.getString(2));
            verTitulo.setText(capitalizeFirstLetter(pi.getString(1)));
            google = pi.getString(1);
            verCategoria.setText(capitalizeFirstLetter(pi.getString(4)));
            byteImagem = pi.getBlob(3);
            Bitmap nbImagem = BitmapFactory.decodeByteArray(byteImagem, 0, byteImagem.length);
            verImagem.setImageBitmap(nbImagem);
        }
        pi.close();

        final LinearLayout openBrowser = (LinearLayout) findViewById(R.id.openBrowser);
        openBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.google.com/search?q="+google));
                startActivity(intent);
            }
        });

        savings = new ArrayList<>();
        savings.clear();
        savingsA = new SavingsAdapter(this, savings);
        frases = new ArrayList<String>();
        frases.clear();
        Cursor sv = bd.getProdutoSavings(idVer);
        savingsRV = (RecyclerView) findViewById(R.id.savingsRV);
        final LinearLayout savingsContainer = (LinearLayout) findViewById(R.id.savingsContainer);
        if(sv != null) {
            sv.moveToPosition(-1);
            while (sv.moveToNext()) {
                int gastoOuGanho = Integer.parseInt(sv.getString(4));
                String mensagemGanho = "You added $" + sv.getString(2) + " dollars to this saving.";
                String mensagemGasto = "You spent $" + sv.getString(2) + " dollars from this saving.";
                SimpleDateFormat inputData = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    dataO = inputData.parse(sv.getString(3));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat helperFormatacao = new SimpleDateFormat("dd/MM/yyyy");
                String dataFormatada = helperFormatacao.format(dataO);
                if (gastoOuGanho == 0) {
                    gastos += Float.parseFloat(sv.getString(2));
                    savings.add(new ProdutosSavings(R.drawable.circulo_vermelho, mensagemGasto, dataFormatada));
                    savingsA.notifyDataSetChanged();
                } else {
                    soma += Float.parseFloat(sv.getString(2));
                    savings.add(new ProdutosSavings(R.drawable.circulo_verde, mensagemGanho, dataFormatada));
                    savingsA.notifyDataSetChanged();
                    frases.add("$" + sv.getString(2) + " - " + dataFormatada);
                }
            }
        }
        sv.close();
        sllm = new LinearLayoutManager(this);
        savingsRV.setLayoutManager(sllm);
        if(savings != null && savings.size() > 0){
            savingsRV.setAdapter(savingsA);
        } else {
            TextView emptySavings = new TextView(this);
            emptySavings.setText("You haven't added savings to this product. Click the Add Savings button to add new savings.");
            LinearLayout.LayoutParams emptyP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            emptyP.setMargins(8,12, 8, 20);
            emptySavings.setLayoutParams(emptyP);
            emptySavings.setGravity(Gravity.CENTER);
            savingsContainer.removeView(savingsRV);
            savingsContainer.addView(emptySavings);
        }
        if(soma >= bAtual){
            //atingiu o valor do produto ou mais
            atual = 1;
        } else {
            if(gastos >= soma){
                atual = 0;
            } else {
                atual = soma-gastos;
            }
        }
        fbP = (atual*1)/bAtual;
        fP = Math.round(fbP*100);
        final ConstraintLayout progressoBox = (ConstraintLayout) findViewById(R.id.progressoBox);
        final ConstraintLayout progressoTexto = (ConstraintLayout) findViewById(R.id.progressoTexto);
        final LinearLayout progresso = (LinearLayout) findViewById(R.id.progresso);
        final LinearLayout percentagemLinar = (LinearLayout) findViewById(R.id.percentagemLinear);
        ConstraintLayout.LayoutParams paramss = (ConstraintLayout.LayoutParams) progresso.getLayoutParams();
        ConstraintLayout.LayoutParams percss = (ConstraintLayout.LayoutParams) percentagemLinar.getLayoutParams();
        float novaPercentagem = 0f;
        float novaMargem = 0f;
        if(fP >= 100){
            fP = 100;
            novaPercentagem = 0.9999f;
            novaMargem = 0.9999f;
        } else {
            novaPercentagem = fP/100f;
            if(fP == 0){
                novaMargem = 1f;
            } else {
                novaMargem = (fP/100f)+0.05f;
            }
        }
        paramss.matchConstraintPercentWidth = novaPercentagem;
        paramss.matchConstraintPercentHeight = 1f;
        percss.matchConstraintPercentWidth = novaMargem;
        percss.matchConstraintPercentHeight = 1f;
        progresso.setLayoutParams(paramss);
        percentagemLinar.setLayoutParams(percss);
        progessoValor.setText(String.valueOf(fP)+"%");
        final Button eContinuarButton2 = (Button) findViewById(R.id.eContinuarButton2);
        final Button eMudarButton = (Button) findViewById(R.id.eMudarButton);
        eContinuarButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirAlerta1("Are you sure you want to set this item as Achieved?", idVer);
            }
        });
        eMudarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirAlerta2("Are you sure you want to remove this item?", idVer);
            }
        });

        final Button addSavings = (Button) findViewById(R.id.addSavings);
        final Button removeSavings = (Button) findViewById(R.id.removeSavings);
        addSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirAlerta3("Add Savings' Value", idVer);
            }
        });
        removeSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirAlerta4("Remove Savings", frases);
            }
        });

    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public void abrirAlerta1(String message, final String idAch){
        // criar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean inseridoC = bd.updateProduto(idAch);
                boolean posInserido = true;
                if(inseridoC == true){
                    posInserido = true;
                }
                if(posInserido == true){
                    Intent voltarAoPrincipal = new Intent(VerActivity.this, PrincipalActivity.class);
                    startActivity(voltarAoPrincipal);
                    finish();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // fechar
            }
        });
        // abrir
        AlertDialog bvdialog = builder.create();
        bvdialog.show();
    }

    public void abrirAlerta2(String message, final String idApagar){
        // criar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int apagado = bd.deleteProduto(idApagar);
                boolean apagadoB = true;
                if(apagado > 0){
                    apagadoB = true;
                }
                if(apagadoB = true){
                    Intent voltarAoPrincipal = new Intent(VerActivity.this, PrincipalActivity.class);
                    startActivity(voltarAoPrincipal);
                    finish();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // fechar
            }
        });
        // abrir
        AlertDialog bvdialog = builder.create();
        bvdialog.show();
    }

    public void abrirAlerta3(String message, final String idInserir){
        // criar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(message);
        final EditText inputSaving = new EditText(this);
        inputSaving.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        inputSaving.setRawInputType(Configuration.KEYBOARD_12KEY);
        builder.setView(inputSaving);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String valorIs = inputSaving.getText().toString();
                boolean inseridoS = bd.inserirSaving(idInserir, valorIs);
                boolean posInserido = false;
                if(inseridoS == true){
                    posInserido = true;
                }
                if(posInserido == true){
                    savings.clear();
                    savingsA.notifyDataSetChanged();
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // fechar
            }
        });
        // abrir
        AlertDialog bvdialog = builder.create();
        bvdialog.show();
    }

    public void abrirAlerta4(final String message, final ArrayList<String> frases){
        // criar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(message);
        // list
        if(frases.size() > 0){
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(VerActivity.this, android.R.layout.select_dialog_singlechoice);
            for(int i=0; i<frases.size(); i++){
                arrayAdapter.add(frases.get(i));
            }
            builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String qual = arrayAdapter.getItem(which);
                    AlertDialog.Builder novoB = new AlertDialog.Builder(VerActivity.this);
                    novoB.setTitle(message);
                    novoB.setMessage("Are you sure you want to remove: \n"+qual);
                    novoB.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    novoB.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String[] partes = qual.split(" - ");
                            String pvalor = partes[0];
                            String pdata = partes[1];
                            boolean removeAs = false;
                            boolean atualizaAtv = false;
                            try {
                                removeAs = bd.updateSavings(partes[0], partes[1]);
                                atualizaAtv = true;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if(removeAs == true){
                                atualizaAtv = true;
                            }
                            if(atualizaAtv = true){
                                frases.clear();
                                finish();
                                startActivity(getIntent());
                            }
                        }
                    });
                    novoB.show();
                }
            });
        } else {
            builder.setMessage("You haven't added savings to this product. Click the Add Savings button to add new savings.");
        }
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // fechar
            }
        });
        // abrir
        AlertDialog bvdialog = builder.create();
        bvdialog.show();
    }


}
