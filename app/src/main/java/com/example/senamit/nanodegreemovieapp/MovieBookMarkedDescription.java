package com.example.senamit.nanodegreemovieapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.senamit.nanodegreemovieapp.Data.MovieContract;
import com.example.senamit.nanodegreemovieapp.Data.MovieContract.*;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MovieBookMarkedDescription extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private Uri currentProductUri;
    TextView txtText;
    TextView txtMovieName;
    TextView txtMovieReleaseDate;
    TextView txtMovieDescr;
    TextView txtMovieRating;
    Target target;

    public static final String LOG_TAG = MovieBookMarkedDescription.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_book_marked_description);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        currentProductUri = intent.getData();

        final ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_id);
        txtMovieName = findViewById(R.id.txt_movie_name);
        txtMovieReleaseDate = findViewById(R.id.txt_movieReleaseDate);
        txtMovieDescr = findViewById(R.id.txt_movie_descr);
        txtMovieRating = findViewById(R.id.movieRating);
            if (currentProductUri!=null){
                getLoaderManager().initLoader(54, null, this);
            }


        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                constraintLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }

        };
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {WishListMovie.COLUMN_MOVIE_NAME, WishListMovie.COLUMN_MOVIE_RELEASE_DATE};
        Log.i(LOG_TAG, "the uri is  "+ currentProductUri  );
        CursorLoader cursorLoader = new CursorLoader(
                this,
                currentProductUri,
                null,
                null,
                null,
                null);
        Log.i(LOG_TAG, "the curosrLoader is  "+cursorLoader);

//        Log.i("TAG", "THE VALUE OF MSELECTION IS "+mSelection);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor==null || cursor.getCount()<1){
            Log.i(LOG_TAG, "inside null block of onloadfinished");
            return;
        }
        int i = cursor.getPosition();
        Log.i(LOG_TAG, "inside on load finisher method "+i);
//        cursor.moveToNext();
//        try {
            while (cursor.moveToNext()){
                Log.i(LOG_TAG, "inside on load finisher method of while block "+i);
                Log.i(LOG_TAG, "inside on load finisher method try blcok ");
                int indexMovieName= cursor.getColumnIndex(WishListMovie.COLUMN_MOVIE_NAME);
                int indexMovieReleaseDate = cursor.getColumnIndex(WishListMovie.COLUMN_MOVIE_RELEASE_DATE);
                int indexMoviePoster = cursor.getColumnIndex(WishListMovie.COLUMN_MOVIE_POSTER);
                int indexMovieRating = cursor.getColumnIndex(WishListMovie.COLUMN_MOVIE_RATING);
                int indexMovieOverview = cursor.getColumnIndex(WishListMovie.COLUMN_MOVIE_OVERVIEW);
                String movieName= cursor.getString(indexMovieName);
                Log.i(LOG_TAG, "the movie name is "+movieName);
                String movieReleaseDate = cursor.getString(indexMovieReleaseDate);
                String moviePoster = cursor.getString(indexMoviePoster);
                String movieRating = cursor.getString(indexMovieRating);
                String movieOverview = cursor.getString(indexMovieOverview);
                txtMovieName.setText(movieName);
                txtMovieReleaseDate.setText(movieReleaseDate);
                txtMovieRating.setText(movieRating);
                txtMovieDescr.setText(movieOverview);
                Picasso.with(this).load(moviePoster).into(target);


            }

            cursor.moveToPosition(-1);

// finally {
//            cursor.close();
//        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        txtText.setText("");
    }

}
