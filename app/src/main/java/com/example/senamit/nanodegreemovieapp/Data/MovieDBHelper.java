package com.example.senamit.nanodegreemovieapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.senamit.nanodegreemovieapp.Data.MovieContract.*;

/**
 * Created by senamit on 2/1/18.
 */

public class MovieDBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME= "MovieTracker";
    public static final int DATABASE_VERSION= 1;

    public MovieDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    private static final String SQL_CREATE_MOVIE_ENTRY = "CREATE TABLE "+ WishListMovie.TABLE_NAME +
            "(" +WishListMovie._ID+" INTEGER PRIMARY KEY, "+
            WishListMovie.COLUMN_MOVIE_NAME+" TEXT NOT NULL, "+
            WishListMovie.COLUMN_MOVIE_RELEASE_DATE+ " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + WishListMovie.TABLE_NAME;



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_MOVIE_ENTRY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

}
