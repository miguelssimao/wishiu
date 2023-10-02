package com.example.wishiu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class AdicionarActivity extends AppCompatActivity {
    BD bd;
    final int REQUEST_CODE_GALLERY = 999;
    ImageView uploadImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);
        bd = new BD(this);
        final ImageButton addImagem = (ImageButton) findViewById(R.id.imageButton);
        uploadImagem = (ImageView) findViewById(R.id.uploadImagem);
        addImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AdicionarActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        final Button acontinuarButton = (Button) findViewById(R.id.acontinuarButton);
        final EditText categoryInput = (EditText) findViewById(R.id.categoryInput);
        final EditText tituloInput = (EditText) findViewById(R.id.tituloInput);
        final EditText priceInput = (EditText) findViewById(R.id.priceInput);
        final EditText startingvInput = (EditText) findViewById(R.id.startingvInput);
        acontinuarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String civ = categoryInput.getText().toString().trim();
                String tiv = tituloInput.getText().toString().trim();
                String piv = priceInput.getText().toString().trim();
                String sviv = startingvInput.getText().toString().trim();
                if(civ.isEmpty() || tiv.isEmpty() || piv.isEmpty()){
                    if(civ.isEmpty()){
                        // categoria
                        abrirAlerta("Category cannot be left blank. Please fill in this space.");
                    } else if(tiv.isEmpty()){
                        // titulo
                        abrirAlerta("Title cannot be left blank. Please fill in this space.");
                    } else if(piv.isEmpty()){
                        // preco
                        abrirAlerta("Price cannot be left blank. Please fill in this space.");
                    } else {
                        // geral
                        abrirAlerta("Field(s) cannot be left blank. Please fill in the required space(s).");
                    }
                } else {
                    final float fpiv = Float.parseFloat(piv);
                    if(fpiv == 0){
                        abrirAlerta("Price cannot be 0. Please fill in this space.");
                    } else {
                        if(sviv.isEmpty()){
                            sviv = "0";
                        } else {
                            final float fsviv = Float.parseFloat(sviv);
                            if(fsviv == 0){
                                sviv = "0";
                            } else {
                                sviv =  new DecimalFormat("#.##").format(fsviv);
                            }
                        }
                        final String dffpiv = new DecimalFormat("#.##").format(fpiv);
                        boolean inseridoProduto = bd.inserirProduto(civ, tiv, dffpiv, imgToByte(uploadImagem), sviv);
                        if(inseridoProduto == true){
                            //mudar de atividade
                            Intent voltar = new Intent(AdicionarActivity.this, PrincipalActivity.class);
                            startActivity(voltar);
                            finish();
                        } else {
                            abrirAlerta("An error occured. Please, try again.");
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent upload = new Intent(Intent.ACTION_PICK);
                upload.setType("image/*");
                startActivityForResult(upload, REQUEST_CODE_GALLERY);
            } else {
                abrirAlerta("You didn't grant permission to access your gallery.");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                int width = this.getResources().getDisplayMetrics().widthPixels;
                int height = (width*bitmap.getHeight())/bitmap.getWidth();
                bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                uploadImagem.setImageBitmap(bitmap);
                uploadImagem.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] imgToByte (ImageView image){
        Bitmap bm = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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
