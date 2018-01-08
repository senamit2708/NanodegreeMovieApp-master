package com.example.senamit.nanodegreemovieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senamit.nanodegreemovieapp.Data.MovieContract;
import com.example.senamit.nanodegreemovieapp.Data.MovieDBHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import com.example.senamit.nanodegreemovieapp.Data.MovieDBHelper.*;
import com.example.senamit.nanodegreemovieapp.Data.MovieContract.*;

public class MovieDetailDescription extends AppCompatActivity {

    private Bundle bundle;
    private MovieDetails movieDetails;
    Target target;
    Button btnBookmark;
    private static final String LOG_TAG = MovieDetailDescription.class.getSimpleName();
    SQLiteDatabase db;
    Cursor cursor;
    MovieDBHelper movieDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        movieDBHelper = new MovieDBHelper(this);

        final TextView txtMovieName = findViewById(R.id.txt_movie_name);
        TextView txtMovieReleaseDate = findViewById(R.id.txt_movieReleaseDate);
        TextView txtMovieDescr = findViewById(R.id.txt_movie_descr);
        TextView txtMovieRating = findViewById(R.id.movieRating);
        btnBookmark = findViewById(R.id.btn_bookmark);
        final ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_id);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        movieDetails = (MovieDetails) bundle.getParcelable("movieDesc");
        txtMovieName.setText(movieDetails.getMovieName());
        txtMovieReleaseDate.setText(movieDetails.getMovieReleaseDate());
        txtMovieDescr.setText(movieDetails.getMovieOverView());
        txtMovieRating.setText(movieDetails.getMovieRating());

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
        Picasso.with(this).load(movieDetails.getMovieImageUrl()).into(target);


        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                db = movieDBHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                String movieName = txtMovieName.getText().toString();
                contentValues.put(WishListMovie.COLUMN_MOVIE_NAME, movieName);
                contentValues.put(WishListMovie.COLUMN_MOVIE_RELEASE_DATE,"1992");
//                long rowId = db.insert(WishListMovie.TABLE_NAME,null, contentValues );
               Uri uriId= getContentResolver().insert(WishListMovie.CONTENT_URI, contentValues);
                if (uriId !=null){
                    Toast.makeText(MovieDetailDescription.this, "successful", Toast.LENGTH_SHORT).show();
                    Log.i(LOG_TAG,"inside the database table");
                }
                else{

                    Toast.makeText(MovieDetailDescription.this, "unsucessful", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }



}
