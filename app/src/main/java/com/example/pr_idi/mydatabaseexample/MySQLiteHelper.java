package com.example.pr_idi.mydatabaseexample;

/**
 * MySQLiteHelper
 * Created by pr_idi on 10/11/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_FILMS = "films";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_YEAR_RELEASE = "year_release";
    public static final String COLUMN_DIRECTOR = "director";
    public static final String COLUMN_PROTAGONIST = "protagonist";
    public static final String COLUMN_CRITICS_RATE = "critics_rate";

    private static final String DATABASE_NAME = "films.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_FILMS + "( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_COUNTRY + " text not null, "
            + COLUMN_YEAR_RELEASE + " integer not null, "
            + COLUMN_DIRECTOR + " text not null, "
            + COLUMN_PROTAGONIST + " text not null, "
            + COLUMN_CRITICS_RATE + " integer"
            + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, "Toy Story");
        values.put(COLUMN_DIRECTOR, "John Lasseter");
        values.put(COLUMN_COUNTRY, "USA");
        values.put(COLUMN_YEAR_RELEASE, 1995);
        values.put(COLUMN_PROTAGONIST, "Tom Hanks");
        values.put(COLUMN_CRITICS_RATE, 8);
        database.insert(TABLE_FILMS, null, values);
        values = new ContentValues();
        values.put(COLUMN_TITLE, "Blade Runner");
        values.put(COLUMN_DIRECTOR, "Ridley Scott");
        values.put(COLUMN_COUNTRY, "USA");
        values.put(COLUMN_YEAR_RELEASE, 1982);
        values.put(COLUMN_PROTAGONIST, "Harrison Ford");
        values.put(COLUMN_CRITICS_RATE, 8);
        database.insert(TABLE_FILMS, null, values);
        values = new ContentValues();
        values.put(COLUMN_TITLE, "Rocky Horror Picture Show");
        values.put(COLUMN_DIRECTOR, "Jim Sharman");
        values.put(COLUMN_COUNTRY, "United Kingdom");
        values.put(COLUMN_YEAR_RELEASE, 1975);
        values.put(COLUMN_PROTAGONIST, "Susan Sarandon");
        values.put(COLUMN_CRITICS_RATE, 7);
        database.insert(TABLE_FILMS, null, values);
        values = new ContentValues();
        values.put(COLUMN_TITLE, "The Godfather");
        values.put(COLUMN_DIRECTOR, "Francis Ford Coppola");
        values.put(COLUMN_COUNTRY, "USA");
        values.put(COLUMN_YEAR_RELEASE, 1972);
        values.put(COLUMN_PROTAGONIST, "Marlon Brando");
        values.put(COLUMN_CRITICS_RATE, 9);
        database.insert(TABLE_FILMS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILMS);
        onCreate(db);
    }

}