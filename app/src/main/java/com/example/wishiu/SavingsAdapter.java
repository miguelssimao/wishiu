package com.example.wishiu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SavingsAdapter extends RecyclerView.Adapter<SavingsAdapter.ViewHolder> {

    private Context sContext;
    private Context viewS;
    private ArrayList<ProdutosSavings> sList;

    SavingsAdapter(Context context, ArrayList<ProdutosSavings> list){
        this.sContext = context;
        this.sList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutI = LayoutInflater.from(sContext);
        View iv = layoutI.inflate(R.layout.item_savings, parent, false);
        ViewHolder vh = new ViewHolder(iv);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        ProdutosSavings savingsi = sList.get(i);
        ImageView scirculo = viewHolder.circulo;
        TextView sm = viewHolder.messageSavings;
        TextView sd = viewHolder.dateSavings;

        scirculo.setImageResource(savingsi.getCirculo());
        sm.setText(savingsi.getMensagem());
        sd.setText(savingsi.getData());
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView circulo;
        TextView messageSavings, dateSavings;

        public ViewHolder(View v){
            super(v);
            circulo = (ImageView) v.findViewById(R.id.circulo);
            messageSavings = (TextView) v.findViewById(R.id.messageSavings);
            dateSavings = (TextView) v.findViewById(R.id.dateSavings);
        }

    }

}
