package com.example.pr_idi.mydatabaseexample;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicola on 02/01/2017.
 */

public class MainCercaProtagonist extends ListActivity {
    FilmData filmDataProta;

    EditText editText;

    private List<Film> values;
    private List<Film> filmsOfProtagonist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_cerca_prota);

        filmDataProta = new FilmData(this);
        filmDataProta.open();

        values = filmDataProta.getAllFilms();
        filmsOfProtagonist = new ArrayList<>();
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filmsOfProtagonist);
        setListAdapter(adapter);

        editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = s.toString();
                setFilmsOfProtagonist(name);
            }
        });

    }

    public void setFilmsOfProtagonist(String name){
        List<Film> filmList = new ArrayList<>();
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) getListAdapter();
        adapter.clear();
        if(!name.isEmpty()){
            for(int i=0; i<values.size(); ++i){
                if(name.compareTo(values.get(i).getProtagonist())==0){
                    adapter.add(values.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        filmDataProta.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        filmDataProta.close();
        super.onPause();
    }
}