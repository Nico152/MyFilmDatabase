package com.example.pr_idi.mydatabaseexample;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class LlistaActivity extends AppCompatActivity {
    private List<Film> llista;
    private RecyclerView RView;
    private FilmData filmData;
    private RecyclerAdapter RAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llistaact);

        filmData = new FilmData(this);
        filmData.open();

        llista = new ArrayList<>();
        llista = filmData.getAllFilms();


        Collections.sort(llista, new Comparator<Film>() {
            @Override
            public int compare(Film lhs, Film rhs) {
                return rhs.getYear() - lhs.getYear();
            }
        });

        RView = (RecyclerView) findViewById(R.id.rec_view);
        RAdapter = new RecyclerAdapter(llista);
        RView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        RView.setAdapter(RAdapter);


    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    /*public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                System.out.println("CAMBIO DE PANTALLA");
                Intent intent = new Intent(LlistaActivity.this, AddActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.delete:
                Collections.sort(llista, new Comparator<Film>() {
                    @Override
                    public int compare(Film lhs, Film rhs) {
                        return rhs.getYear() - lhs.getYear();
                    }
                });
                break;
        }
    }
*/


    @Override
    protected void onResume() {
        filmData.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        filmData.close();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        Film peli = (Film)intent.getSerializableExtra("result");
        if (peli != null) {
            llista.add(peli);
            RAdapter.notifyDataSetChanged();
        }


        System.out.println("HE VUELTO");
    }

}