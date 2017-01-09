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
        MainFilmAdapter adapter = new MainFilmAdapter(this, R.layout.recyclerview_item_row, (ArrayList) filmsOfProtagonist);
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
        char mayustominus = 'a'-'A';
        if(!name.isEmpty()){
            for(int i=0; i<values.size(); ++i){
                boolean add = true;
                String namevalue = values.get(i).getProtagonist();
                for(int r = 0; r < name.length() && r < namevalue.length();++r){
                    if(name.charAt(r)!=namevalue.charAt(r) &&
                            (name.charAt(r)+mayustominus)!=namevalue.charAt(r) &&
                            (name.charAt(r)-mayustominus)!=namevalue.charAt(r)){
                        add = false;
                        break;
                    }
                }
                if(add && namevalue.length()>=name.length()) adapter.add(values.get(i));
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