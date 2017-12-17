package com.example.senamit.nanodegreemovieapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MovieDetailDescription extends AppCompatActivity {

    private Bundle bundle;
    private MovieDetails movieDetails;
    Target target;
    private static final String LOG_TAG = MovieDetailDescription.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView txtMovieName = findViewById(R.id.txt_movie_name);
        TextView txtMovieReleaseDate = findViewById(R.id.txt_movieReleaseDate);
        TextView txtMovieDescr = findViewById(R.id.txt_movie_descr);
        final ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_id);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        movieDetails = (MovieDetails) bundle.getParcelable("movieDesc");
        txtMovieName.setText(movieDetails.getMovieName());
        txtMovieReleaseDate.setText(movieDetails.getMovieReleaseDate());
        txtMovieDescr.setText(movieDetails.getMovieOverView());


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
    }

}
