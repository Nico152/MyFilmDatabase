package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicola on 02/01/2017.
 */

public class MainCercaEsborraTitol extends ListActivity {
    FilmData filmDataProta;

    EditText editText;

    public static Button btn;

    private List<Film> values;
    private List<Film> filmsOfProtagonist;
    private ArrayAdapter<Film> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_cerca_esborra_titol);

        filmDataProta = new FilmData(this);
        filmDataProta.open();

        values = filmDataProta.getAllFilms();
        filmsOfProtagonist = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filmsOfProtagonist);
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

        ListView lv1=getListView();
        registerForContextMenu(lv1);



        ///////////////////////////////////////
        ///////////////////////////////////////


        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainCercaEsborraTitol.this);
            alertDialogBuilder.setTitle("Delete");

            alertDialogBuilder
                .setMessage("Do you want to delete this film?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog,int id)
                        {
                            //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            //int position = info.position;
                            Film film = filmsOfProtagonist.get(position);
                            filmDataProta.deleteFilm(film);
                            filmsOfProtagonist.remove(position);
                            Toast.makeText(getApplicationContext(),"Film deleted successfully.",Toast.LENGTH_LONG).show();
                            adapter.notifyDataSetChanged();
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

    public void setFilmsOfProtagonist(String name){
        List<Film> filmList = new ArrayList<>();
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) getListAdapter();
        adapter.clear();
        char mayustominus = 'a'-'A';
        if(!name.isEmpty()){
            for(int i=0; i<values.size(); ++i){
                boolean add = true;
                String namevalue = values.get(i).getTitle();
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
            Intent intent = new Intent(MainCercaEsborraTitol.this,ModifyFilmRate.class);
            intent.putExtra("FILM_ID",film.getId());
            intent.putExtra("FILM_TITLE",film.getTitle());
            intent.putExtra("FILM_RATE",film.getCritics_rate());
            startActivity(intent);
        }
        else if(item.getTitle()=="Delete film"){
            Film film = filmsOfProtagonist.get(position);
            filmDataProta.deleteFilm(film);
            filmsOfProtagonist.remove(position);
            Toast.makeText(getApplicationContext(),"Film deleted successfully.",Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        }else{
            return false;
        }
        return true;
    }


}