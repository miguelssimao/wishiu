package com.example.wishiu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProdutosAdaptera extends RecyclerView.Adapter<ProdutosAdaptera.ViewHolder> {

    private Context paContext;
    private Context viewCa;
    private ArrayList<ProdutosWishes> paList;

    ProdutosAdaptera(Context context, ArrayList<ProdutosWishes> list){
        this.paContext = context;
        this.paList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutIa = LayoutInflater.from(paContext);
        View iva = layoutIa.inflate(R.layout.item_produtoa, parent, false);
        ViewHolder vha = new ViewHolder(iva);
        return vha;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        ProdutosWishes produtoia = paList.get(i);
        ImageView pimagem = viewHolder.imagemProdutoa;
        TextView ptitulo = viewHolder.infoTituloa;
        TextView pcategoria = viewHolder.infoCategoriaa;
        TextView pid = viewHolder.produtoaID;

        byte[] novaaImagem = produtoia.getImagem();
        Bitmap novoa = BitmapFactory.decodeByteArray(novaaImagem, 0, novaaImagem.length);
        pimagem.setImageBitmap(novoa);
        ptitulo.setText(produtoia.getTitulo());
        pcategoria.setText(produtoia.getCategoria());
        pid.setText(""+produtoia.getIdPw()+"");
    }

    @Override
    public int getItemCount() {
        return paList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        ImageView imagemProdutoa;
        TextView infoTituloa, infoCategoriaa, produtoaID;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            viewCa = v.getContext();
            imagemProdutoa = (ImageView) v.findViewById(R.id.imagemProdutoa);
            infoTituloa = (TextView) v.findViewById(R.id.infoTituloa);
            infoCategoriaa = (TextView) v.findViewById(R.id.infoCategoriaa);
            produtoaID = (TextView) v.findViewById(R.id.idViewa);
        }

        @Override
        public void onClick(View v){
            String iddisto_a = produtoaID.getText().toString();
            Intent dataproduto = new Intent(viewCa, DefinirDataActivity.class);
            dataproduto.putExtra("idItem", iddisto_a);
            viewCa.startActivity(dataproduto);
        }

    }

}
