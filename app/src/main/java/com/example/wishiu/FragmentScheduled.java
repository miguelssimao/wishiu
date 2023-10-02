package com.example.wishiu;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

public class FragmentScheduled extends Fragment {
    BD bd;
    RecyclerView rvsc;
    LinearLayoutManager llmsc;
    ArrayList<ProdutosWishes> produtossc;
    ProdutosAdaptersc produtosAsc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scheduled, container, false);
        bd = new BD(getActivity());

        produtossc = new ArrayList<>();
        produtossc.clear();
        produtosAsc = new ProdutosAdaptersc(getActivity(), produtossc);
        Cursor ppsc = bd.getScheduled();
        final RelativeLayout rlWishessc = (RelativeLayout) rootView.findViewById(R.id.rlScheduled);
        rvsc = (RecyclerView) rootView.findViewById(R.id.rvS);

        if(ppsc != null){
            ppsc.moveToPosition(-1);
            while (ppsc.moveToNext()) {
                Cursor ppsci = bd.getProdutoAchieved(ppsc.getString(1));
                String prcp = null;
                try {
                    prcp = String.valueOf(bd.getDiferencaDeDias(ppsc.getString(1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(ppsci != null && ppsci.moveToFirst()){
                    String titp = capitalizeFirstLetter(ppsci.getString(1));
                    String catp = capitalizeFirstLetter(ppsci.getString(4));
                    byte[] imgp = ppsci.getBlob(3);
                    produtossc.add(new ProdutosWishes(imgp, prcp, titp, catp, ppsc.getInt(1)));
                    produtosAsc.notifyDataSetChanged();
                }
                ppsci.close();
            }
        }
        ppsc.close();

        llmsc = new LinearLayoutManager(getActivity());
        rvsc.setLayoutManager(llmsc);
        if(produtossc != null && produtossc.size() > 0){
            rvsc.setAdapter(produtosAsc);
        } else {
            TextView emptyWishessc = new TextView(getActivity());
            emptyWishessc.setText("You haven't set arrival dates for any of your achieved wishes. When you do, they will be added to this list.");
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            params.setMargins(24,0,24,0);
            emptyWishessc.setLayoutParams(params);
            emptyWishessc.setGravity(Gravity.CENTER);
            rlWishessc.removeView(rvsc);
            rlWishessc.addView(emptyWishessc);
        }

        // return no final
        return rootView;
    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

}
