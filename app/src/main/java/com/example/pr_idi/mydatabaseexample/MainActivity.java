package com.example.pr_idi.mydatabaseexample;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.ListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
    private FilmData filmData;

    private ArrayAdapter<Film> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms();
        Comparator<Film> cmp = new Comparator<Film>() {
            @Override
            public int compare(Film lhs, Film rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        };
        Collections.sort(values,cmp);  //Ara tenim les pelicules ordenades per titol

        // use the SimpleCursorAdapter to show the
        // elements in a ListView


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        ListView drawerList = (ListView) findViewById(R.id.nav_view);
        String[] strings = {"Action1","Action2"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,R.layout.drawer_list_item,strings);
        drawerList.setAdapter(adapter1);
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
        super.onResume();
    }

    @Override
    protected void onPause() {
        filmData.close();
        super.onPause();
    }

}