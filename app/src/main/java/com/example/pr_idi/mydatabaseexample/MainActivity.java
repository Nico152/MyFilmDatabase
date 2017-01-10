package com.example.pr_idi.mydatabaseexample;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FilmData filmData;

    private MainFilmAdapter adapter;
    List<Film> values;
    ListView lv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        filmData = new FilmData(this);
        filmData.open();

        lv1 =(ListView)findViewById(R.id.list1);
        lv1.setOnCreateContextMenuListener(this);
        getData(); //Ara tenim les pel·licules ordenades per titol

        //Buttons:

        (findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainCercaProtagonist.class);
                startActivity(intent);
            }
        });

        (findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LlistaActivity.class);
                startActivity(intent);
            }
        });

        (findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        (findViewById(R.id.button5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainCercaEsborraTitol.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        ///////////////////////////////////////
        ///////////////////////////////////////


        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Edit rate");

                alertDialogBuilder
                        .setMessage("Do you want to edit the critics rate?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id)
                            {
                                Film film = adapter.getItem(position);
                                Intent intent = new Intent(MainActivity.this,ModifyFilmRate.class);
                                intent.putExtra("FILM_ID",film.getId());
                                intent.putExtra("FILM_TITLE",film.getTitle());
                                intent.putExtra("FILM_RATE",film.getCritics_rate());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id)
                            {

                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });




        ///////////////////////////////////////
        ///////////////////////////////////////




    }

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
        adapter = new MainFilmAdapter(this, R.layout.recyclerview_item_row,(ArrayList)values);
        lv1.setAdapter(adapter);
    }


    /////////////////
    ////CONTEXT MENU:
    /////////////////

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select action:");
        menu.add(0, v.getId(), 0, "Edit rate");
        menu.add(0, v.getId(), 0, "Delete film");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        if(item.getTitle()=="Edit rate"){
            Film film = adapter.getItem(position);
            Intent intent = new Intent(MainActivity.this,ModifyFilmRate.class);
            intent.putExtra("FILM_ID",film.getId());
            intent.putExtra("FILM_TITLE",film.getTitle());
            intent.putExtra("FILM_RATE",film.getCritics_rate());
            startActivity(intent);
        }
        else if(item.getTitle()=="Delete film"){
            Film film = values.get(position);
            filmData.deleteFilm(film);
            values.remove(position);
            Toast.makeText(getApplicationContext(),"Film deleted successfully.",Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        }else{
            return false;
        }
        return true;
    }



}