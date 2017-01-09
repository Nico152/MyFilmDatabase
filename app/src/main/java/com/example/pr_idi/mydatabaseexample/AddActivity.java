package com.example.pr_idi.mydatabaseexample;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class AddActivity extends AppCompatActivity {

    private FilmData filmData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);


        filmData = new FilmData(this);
        filmData.open();
        System.out.println("INICIO");
    }

    public void onClick(View view) {


    }
        //adapter.notifyDataSetChanged();



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

    public void Enviar(View view) {

        System.out.println("GLOBAAAAAL");

        switch (view.getId()) {
            case R.id.buttonAddFilm:

                System.out.println("LE HAS DADO AL BOTONCITO");

                EditText title = (EditText) findViewById(R.id.editTitle);
                String str_title = title.getText().toString();

                EditText director = (EditText) findViewById(R.id.editDirector);
                String str_director = director.getText().toString();

                EditText country = (EditText) findViewById(R.id.editCountry);
                String str_country = country.getText().toString();

                EditText year = (EditText) findViewById(R.id.editYear);
                String str_year = year.getText().toString();

                EditText protagonist = (EditText) findViewById(R.id.editProtagonist);
                String str_protagonist = protagonist.getText().toString();

                EditText critics_rate = (EditText) findViewById(R.id.editRate);
                String str_critics_rate = critics_rate.getText().toString();




                if (str_title.isEmpty() || str_director.isEmpty() || str_country.isEmpty() || str_year.isEmpty() || str_protagonist.isEmpty() || str_critics_rate.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Don't leave empty values!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (Integer.parseInt(str_critics_rate) < 0 || Integer.parseInt(str_critics_rate) > 10)
                    {
                        Toast.makeText(getApplicationContext(),"The rate must be between 0 and 10!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        List<Film> vf = new ArrayList<>();
                        vf = (ArrayList)filmData.getAllFilms();
                        boolean titol_repetit = false;
                        int i = 0;
                        while(i < vf.size() && titol_repetit == false)
                        {
                            if (vf.get(i).getTitle().equals(str_title))
                            {
                                titol_repetit = true;
                            }
                            i++;
                        }

                        if (titol_repetit)
                        {
                            Toast.makeText(getApplicationContext(),"Film title already exists!",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Film peli = new Film();
                            peli = filmData.createFilm(str_title, str_director, str_country, Integer.parseInt(str_year), str_protagonist, Integer.parseInt(str_critics_rate));
                            Toast.makeText(getApplicationContext(), "Film added successfully.", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                break;
        }
    }

}