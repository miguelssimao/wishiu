package com.example.wishiu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ViewHolder> {

    private Context pContext;
    private Context viewC;
    private ArrayList<ProdutosWishes> pList;

    ProdutosAdapter(Context context, ArrayList<ProdutosWishes> list){
        this.pContext = context;
        this.pList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutI = LayoutInflater.from(pContext);
        View iv = layoutI.inflate(R.layout.item_produto, parent, false);
        ViewHolder vh = new ViewHolder(iv);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        ProdutosWishes produtoi = pList.get(i);
        ImageView pimagem = viewHolder.imagemProduto;
        TextView ptitulo = viewHolder.infoTitulo;
        TextView pcategoria = viewHolder.infoCategoria;
        TextView ppreco = viewHolder.precoProduto;
        TextView pid = viewHolder.produtoID;

        byte[] novaImagem = produtoi.getImagem();
        Bitmap novo = BitmapFactory.decodeByteArray(novaImagem, 0, novaImagem.length);
        pimagem.setImageBitmap(novo);
        ptitulo.setText(produtoi.getTitulo());
        pcategoria.setText(produtoi.getCategoria());
        ppreco.setText(produtoi.getPreco());
        pid.setText(""+produtoi.getIdPw()+"");
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        ImageView imagemProduto;
        TextView infoTitulo, infoCategoria, precoProduto, produtoID;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            viewC = v.getContext();
            imagemProduto = (ImageView) v.findViewById(R.id.imagemProduto);
            infoTitulo = (TextView) v.findViewById(R.id.infoTitulo);
            infoCategoria = (TextView) v.findViewById(R.id.infoCategoria);
            precoProduto = (TextView) v.findViewById(R.id.precoProduto);
            produtoID = (TextView) v.findViewById(R.id.idView);
        }

        @Override
        public void onClick(View v){
            Intent verproduto = new Intent(viewC, VerActivity.class);
            String vidi = produtoID.getText().toString();
            verproduto.putExtra("idItem", vidi);
            viewC.startActivity(verproduto);
        }

    }

}
