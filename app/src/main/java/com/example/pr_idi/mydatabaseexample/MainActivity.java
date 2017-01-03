package com.example.pr_idi.mydatabaseexample;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
    private FilmData filmData;

    private ArrayAdapter<Film> adapter;
    List<Film> values;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        filmData = new FilmData(this);
        filmData.open();

        getData(); //Ara tenim les pelicules ordenades per titol

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Film film = adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this,ModifyFilmRate.class);
                intent.putExtra("FILM_ID",film.getId());
                intent.putExtra("FILM_TITLE",film.getTitle());
                intent.putExtra("FILM_RATE",film.getCritics_rate());
                startActivity(intent);

            }
        });

        (findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainCercaProtagonist.class);
                startActivity(intent);
            }
        });
    }
    // Will be called via the onClick attribute
    // of the buttons in main.xml
    /*public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) getListAdapter();
        Film film;
        switch (view.getId()) {
            case R.id.add:
                String[] newFilm = new String[] { "Blade Runner", "Ridley Scott", "Rocky Horror Picture Show", "Jim Sharman", "The Godfather", "Francis Ford Coppola", "Toy Story", "John Lasseter" };
                int nextInt = new Random().nextInt(4);
                // save the new film to the database
                film = filmData.createFilm(newFilm[nextInt*2], newFilm[nextInt*2 + 1]);
                adapter.add(film);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    film = (Film) getListAdapter().getItem(0);
                    filmData.deleteFilm(film);
                    adapter.remove(film);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }*/

    @Override
    protected void onResume() {
        filmData.open();
        getData();
        super.onResume();
    }

    @Override
    protected void onPause() {
        filmData.close();
        super.onPause();
    }

    public void getData(){
        values = filmData.getAllFilms();
        Comparator<Film> cmp = new Comparator<Film>() {
            @Override
            public int compare(Film lhs, Film rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        };
        Collections.sort(values,cmp);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    /*private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position){
        if(position == 0){
            Intent intent = new Intent(MainActivity.this, MainCercaProtagonist.class);
            startActivity(intent);
        }
    }*/
}