package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Nicola on 02/01/2017.
 */

public class ModifyFilmRate extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_rate);

        String title = getIntent().getStringExtra("FILM_TITLE");
        int rate = getIntent().getIntExtra("FILM_RATE",0);

        TextView board = (TextView)findViewById(R.id.board);
        board.setText(title + " is rated with a " + rate);

    }

}
