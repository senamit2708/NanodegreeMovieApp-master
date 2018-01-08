package com.example.senamit.nanodegreemovieapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.senamit.nanodegreemovieapp.Data.MovieContract;
import com.example.senamit.nanodegreemovieapp.Data.MovieContract.*;

import com.example.senamit.nanodegreemovieapp.Data.MovieDBHelper;

import java.util.ArrayList;

public class MovieBookmarkedList extends AppCompatActivity {

    Cursor cursor;
    SQLiteDatabase db;
    MovieDBHelper movieDBHelper;
    TextView displayText;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_bookmarked_list);

        movieDBHelper = new MovieDBHelper(this);

//        displayText = findViewById(R.id.txt_movieboorkmarked);
        arrayList = new ArrayList<String>();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);


        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(arrayAdapter);
//        RecyclerView recyclerView= findViewById(R.id.recycler_movieBookmarked);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setAdapter(arrayAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        cursor = queryAllMoives();
        displayDataBaseInfo(cursor);
    }

    private void displayDataBaseInfo(Cursor cursor) {
        try{


            int idColumnIndex = cursor.getColumnIndex(WishListMovie._ID);
            int movieNameColumnIndex = cursor.getColumnIndex(WishListMovie.COLUMN_MOVIE_NAME);
            int releaseDataColumnIndex = cursor.getColumnIndex(WishListMovie.COLUMN_MOVIE_RELEASE_DATE);
            while (cursor.moveToNext()){
                String movieName = cursor.getString(movieNameColumnIndex);
//                displayText.append("\n" + movieName);
                arrayList.add(movieName);

            }

        }finally {
            cursor.close();
        }


    }


    private Cursor queryAllMoives() {
        cursor = getContentResolver().query(WishListMovie.CONTENT_URI,
                null,
                null,
                null,
                null);
        return cursor;
    }
}
