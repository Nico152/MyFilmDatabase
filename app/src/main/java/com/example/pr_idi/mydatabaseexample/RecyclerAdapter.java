package com.example.pr_idi.mydatabaseexample;

/**
 * Created by Guillem on 03/01/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Film> llista;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title, director, country, year, protagonist, critics_rate;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            director = (TextView) view.findViewById(R.id.director);
            country = (TextView) view.findViewById(R.id.country);
            year = (TextView) view.findViewById(R.id.year);
            protagonist = (TextView) view.findViewById(R.id.protagonist);
            critics_rate = (TextView) view.findViewById(R.id.critics_rate);
        }
    }

    public RecyclerAdapter(List<Film> moviesList) {
        this.llista = moviesList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Film peli = llista.get(position);
        holder.title.setText(peli.getTitle());
        holder.director.setText(peli.getDirector());
        holder.country.setText(peli.getCountry());
        holder.year.setText(String.valueOf(peli.getYear()));
        holder.protagonist.setText(peli.getProtagonist());
        holder.critics_rate.setText(String.valueOf(peli.getCritics_rate()));
    }

    @Override
    public int getItemCount() {
        return llista.size();
    }



}