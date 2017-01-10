package com.example.pr_idi.mydatabaseexample;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddActivity extends AppCompatActivity {

    private FilmData filmData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);
        filmData = new FilmData(this);
        filmData.open();
    }

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

        switch (view.getId()) {
            case R.id.buttonAddFilm:

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
                    Toast.makeText(getApplicationContext(),R.string.add_film_msg1,Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (Integer.parseInt(str_critics_rate) < 0 || Integer.parseInt(str_critics_rate) > 10)
                    {
                        Toast.makeText(getApplicationContext(),R.string.add_film_msg2,Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getApplicationContext(),R.string.add_film_msg3,Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Film peli = new Film();
                            peli = filmData.createFilm(str_title, str_director, str_country, Integer.parseInt(str_year), str_protagonist, Integer.parseInt(str_critics_rate));
                            Toast.makeText(getApplicationContext(), R.string.add_film_msg4, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                break;
        }
    }

}