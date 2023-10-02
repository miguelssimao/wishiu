package com.example.wishiu;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.support.v7.widget.PopupMenu;

public class PrincipalActivity extends AppCompatActivity {

    ViewPager viewP;
    PagesAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        final ImageButton adicionar = (ImageButton) findViewById(R.id.adicionar);

        final Button wishes = (Button) findViewById(R.id.wishes);
        final Button achieved = (Button) findViewById(R.id.achieved);
        final Button scheduled = (Button) findViewById(R.id.scheduled);

        viewP = (ViewPager) findViewById(R.id.viewP);
        pAdapter = new PagesAdapter(getSupportFragmentManager());
        viewP.setAdapter(pAdapter);

        final ImageButton settings = (ImageButton) findViewById(R.id.settings);
        final PopupMenu opcoes = new PopupMenu(this, settings);
        final Menu menu = opcoes.getMenu();
        menu.add(0, 0, 0, "Settings");
        opcoes.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 0:
                        Intent definicoes = new Intent(PrincipalActivity.this, SettingsActivity.class);
                        startActivity(definicoes);
                        return true;
                }
                return false;
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcoes.show();
            }
        });

        wishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonWishes(wishes, achieved, scheduled);
                viewP.setCurrentItem(0);
            }
        });
        achieved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAchieved(achieved, wishes, scheduled);
                viewP.setCurrentItem(1);
            }
        });
        scheduled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonScheduled(scheduled, wishes, achieved);
                viewP.setCurrentItem(2);
            }
        });

        viewP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //ao deslizar
                if(position == 0) {
                    buttonWishes(wishes, achieved, scheduled);
                } else if(position == 1) {
                    buttonAchieved(achieved, wishes, scheduled);
                } else if(position == 2) {
                    buttonScheduled(scheduled, wishes, achieved);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        adicionar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalActivity.this, AdicionarActivity.class);
                startActivity(intent);
            }
        });


    }

    public void buttonWishes(Button wishes, Button achieved, Button scheduled){
        wishes.setBackgroundResource(R.drawable.selecao);
        wishes.setTextColor(getResources().getColor(R.color.textoLaranja));
            achieved.setBackgroundColor(getResources().getColor(R.color.white));
            achieved.setTextColor(getResources().getColor(R.color.textoCinza));
                scheduled.setBackgroundColor(getResources().getColor(R.color.white));
                scheduled.setTextColor(getResources().getColor(R.color.textoCinza));
    }

    public void buttonAchieved(Button achieved, Button wishes, Button scheduled){
        achieved.setBackgroundResource(R.drawable.selecao);
        achieved.setTextColor(getResources().getColor(R.color.textoLaranja));
            wishes.setBackgroundColor(getResources().getColor(R.color.white));
            wishes.setTextColor(getResources().getColor(R.color.textoCinza));
                scheduled.setBackgroundColor(getResources().getColor(R.color.white));
                scheduled.setTextColor(getResources().getColor(R.color.textoCinza));
    }

    public void buttonScheduled (Button scheduled, Button wishes, Button achieved){
        scheduled.setBackgroundResource(R.drawable.selecao);
        scheduled.setTextColor(getResources().getColor(R.color.textoLaranja));
            wishes.setBackgroundColor(getResources().getColor(R.color.white));
            wishes.setTextColor(getResources().getColor(R.color.textoCinza));
                achieved.setBackgroundColor(getResources().getColor(R.color.white));
                achieved.setTextColor(getResources().getColor(R.color.textoCinza));
    }

}
