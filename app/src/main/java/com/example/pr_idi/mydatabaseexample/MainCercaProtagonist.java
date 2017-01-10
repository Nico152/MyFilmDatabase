package com.example.pr_idi.mydatabaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainCercaProtagonist extends AppCompatActivity {
    FilmData filmDataProta;

    EditText editText;

    private List<Film> values;
    private List<Film> filmsOfProtagonist;

    private ListView lv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_cerca_prota);

        filmDataProta = new FilmData(this);
        filmDataProta.open();

        values = filmDataProta.getAllFilms();
        filmsOfProtagonist = new ArrayList<>();
        MainFilmAdapter adapter = new MainFilmAdapter(this, R.layout.recyclerview_item_row, (ArrayList) filmsOfProtagonist);
        lv1 = (ListView) findViewById(R.id.list);
        lv1.setOnCreateContextMenuListener(this);
        lv1.setAdapter(adapter);

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
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) lv1.getAdapter();
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
        filmsOfProtagonist.clear();
        values = filmDataProta.getAllFilms();
        MainFilmAdapter adapter = new MainFilmAdapter(this, R.layout.recyclerview_item_row, (ArrayList) filmsOfProtagonist);
        lv1.setAdapter(adapter);
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
            Film film = filmsOfProtagonist.get(position);
            Intent intent = new Intent(MainCercaProtagonist.this,ModifyFilmRate.class);
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
            ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) lv1.getAdapter();
            adapter.notifyDataSetChanged();
        }else{
            return false;
        }
        return true;
    }


}