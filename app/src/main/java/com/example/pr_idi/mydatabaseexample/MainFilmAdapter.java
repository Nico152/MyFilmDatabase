package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class MainFilmAdapter extends ArrayAdapter<Film> {
    private List<Film> llista;
    private Film film;

    public MainFilmAdapter(Context context, int textViewResourceId, ArrayList<Film> films){
        super(context,textViewResourceId,films);
        this.llista = films;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.recyclerview_item_row,null);
        }

        film = llista.get(position);
        if(film != null){
            TextView titol = (TextView) view.findViewById(R.id.title);
            TextView year = (TextView) view.findViewById(R.id.year);
            TextView director = (TextView) view.findViewById(R.id.director);
            TextView prota = (TextView) view.findViewById(R.id.protagonist);
            TextView rate = (TextView) view.findViewById((R.id.critics_rate));
            TextView country = (TextView) view.findViewById(R.id.country);
            titol.setText(film.getTitle());
            year.setText(String.valueOf(film.getYear()));
            director.setText(film.getDirector());
            prota.setText(film.getProtagonist());
            rate.setText(String.valueOf(film.getCritics_rate()));
            country.setText(film.getCountry());
        }

        view.setFocusable(false);
        view.setFocusableInTouchMode(false );

        return view;
    }
}
