package com.example.senamit.nanodegreemovieapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.senamit.nanodegreemovieapp.Data.MovieContract;
import com.example.senamit.nanodegreemovieapp.Data.MovieContract.*;

import com.example.senamit.nanodegreemovieapp.Data.MovieDBHelper;

import java.util.ArrayList;

public class MovieBookmarkedList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

//    Cursor cursor;
//    SQLiteDatabase db;
//    MovieDBHelper movieDBHelper;
//    TextView displayText;
//    ArrayList<String> arrayList;

    public static final String LOG_TAG = MovieBookmarkedList.class.getSimpleName();
    RecyclerView mRecyclerView;
    CustomCursorBookmarkMovieAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_bookmarked_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerViewBookmarked);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new CustomCursorBookmarkMovieAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        getLoaderManager().initLoader(29, null, this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mMovieCursor = null;

            @Override
            protected void onStartLoading() {
                if (mMovieCursor!=null){
                    deliverResult(mMovieCursor);
                }
                else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try{

        return getContentResolver().query(WishListMovie.CONTENT_URI,
                null,
                null,
                null,
                null);


                }catch (Exception e){
                    Log.e(LOG_TAG, "failed to load data");
                }

                return null;
            }

            @Override
            public void deliverResult(Cursor data) {
                mMovieCursor=data;
                super.deliverResult(data);
            }
        };




    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mAdapter.swapCursor(data);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mAdapter.swapCursor(null);
    }

//        movieDBHelper = new MovieDBHelper(this);
//
////        displayText = findViewById(R.id.txt_movieboorkmarked);
//        arrayList = new ArrayList<String>();
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
//
//
//        ListView listView = findViewById(R.id.listview);
//        listView.setAdapter(arrayAdapter);
////        RecyclerView recyclerView= findViewById(R.id.recycler_movieBookmarked);
////        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
////        recyclerView.setLayoutManager(mLayoutManager);
////        recyclerView.setAdapter(arrayAdapter);
//
//    }
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        cursor = queryAllMoives();
//        displayDataBaseInfo(cursor);
//    }
//
//    private void displayDataBaseInfo(Cursor cursor) {
//        try{
//
//
//            int idColumnIndex = cursor.getColumnIndex(WishListMovie._ID);
//            int movieNameColumnIndex = cursor.getColumnIndex(WishListMovie.COLUMN_MOVIE_NAME);
//            int releaseDataColumnIndex = cursor.getColumnIndex(WishListMovie.COLUMN_MOVIE_RELEASE_DATE);
//            while (cursor.moveToNext()){
//                String movieName = cursor.getString(movieNameColumnIndex);
////                displayText.append("\n" + movieName);
//                arrayList.add(movieName);
//
//            }
//
//        }finally {
//            cursor.close();
//        }
//
//
//    }
//
//
//    private Cursor queryAllMoives() {
//        cursor = getContentResolver().query(WishListMovie.CONTENT_URI,
//                null,
//                null,
//                null,
//                null);
//        return cursor;
//    }
}
