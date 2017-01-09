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

public class MainActivity extends ListActivity {
    private FilmData filmData;

    private MainFilmAdapter adapter;
    List<Film> values;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        filmData = new FilmData(this);
        filmData.open();

        getData(); //Ara tenim les pelicules ordenades per titol

        /*listView = getListView();
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
        });*/

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


        //ListView lv1=getListView();
        //registerForContextMenu(lv1);
        ListView lv1 = getListView();
        lv1.setOnCreateContextMenuListener(this);






        ///////////////////////////////////////
        ///////////////////////////////////////


        //ListView listView = getListView();
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
        adapter = new MainFilmAdapter(this, R.layout.recyclerview_item_row,(ArrayList)values);
        setListAdapter(adapter);
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