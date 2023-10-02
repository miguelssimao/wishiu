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

public class ProdutosAdaptersc extends RecyclerView.Adapter<ProdutosAdaptersc.ViewHolder> {

    private Context pscContext;
    private Context viewCsc;
    private ArrayList<ProdutosWishes> pscList;

    ProdutosAdaptersc(Context context, ArrayList<ProdutosWishes> list){
        this.pscContext = context;
        this.pscList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutIsc = LayoutInflater.from(pscContext);
        View ivsc = layoutIsc.inflate(R.layout.item_produtosc, parent, false);
        ViewHolder vhsc = new ViewHolder(ivsc);
        return vhsc;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        ProdutosWishes produtoisc = pscList.get(i);
        ImageView pimagem = viewHolder.imagemProdutosc;
        TextView pdias = viewHolder.diasSC;
        TextView ptitulo = viewHolder.infoTitulosc;
        TextView pcategoria = viewHolder.infoCategoriasc;
        TextView pid = viewHolder.produtoscID;

        byte[] novascImagem = produtoisc.getImagem();
        Bitmap novosc = BitmapFactory.decodeByteArray(novascImagem, 0, novascImagem.length);
        pimagem.setImageBitmap(novosc);
        ptitulo.setText(produtoisc.getTitulo());
        pcategoria.setText(produtoisc.getCategoria());
        pdias.setText(produtoisc.getPreco());
        pid.setText(""+produtoisc.getIdPw()+"");
    }

    @Override
    public int getItemCount() {
        return pscList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        ImageView imagemProdutosc;
        TextView infoTitulosc, infoCategoriasc, produtoscID, diasSC;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            viewCsc = v.getContext();
            imagemProdutosc = (ImageView) v.findViewById(R.id.imagemProdutosc);
            diasSC = (TextView) v.findViewById(R.id.precoProdutosc);
            infoTitulosc = (TextView) v.findViewById(R.id.infoTitulosc);
            infoCategoriasc = (TextView) v.findViewById(R.id.infoCategoriasc);
            produtoscID = (TextView) v.findViewById(R.id.idViewsc);
        }

        @Override
        public void onClick(View v){
            String iddisto_sc = produtoscID.getText().toString();
            Intent dataproduto = new Intent(viewCsc, DefinirDataScActivity.class);
            dataproduto.putExtra("idItem", iddisto_sc);
            viewCsc.startActivity(dataproduto);
        }

    }

}
