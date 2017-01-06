package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Nicola on 02/01/2017.
 */

public class ModifyFilmRate extends Activity {

    FilmData filmData;
    EditText editText;
    TextView board;
    TextView subtitle;

    long id;
    String title;
    int rate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_rate);

        id = getIntent().getLongExtra("FILM_ID",0);
        title = getIntent().getStringExtra("FILM_TITLE");
        rate = getIntent().getIntExtra("FILM_RATE",0);

        board = (TextView)findViewById(R.id.board);
        board.setText(title + " is rated with a " + rate);

        subtitle = (TextView)  findViewById(R.id.textView2);
        subtitle.setText("Enter a new rate between 0 and 10");

        filmData = new FilmData(this);
        filmData.open();

        editText = (EditText) findViewById(R.id.editText2);


    }

    public void OnClick(View view){
        if(editText.getText().length() > 0){
            int newrate = Integer.parseInt(editText.getText().toString());
            if(newrate >=0 && newrate <=10){
                if(newrate == rate){
                    subtitle.setText("It already has this rate!");
                }
                else{
                    filmData.changeFilmrate(id,newrate);
                    board.setText(title + " is rated with a " + newrate);
                    subtitle.setText("Film rate changed");
                    rate = newrate;
                }
            }
            else{
                subtitle.setText("The rate must be between 0 and 10!");
            }
        }
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
}