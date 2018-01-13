package com.example.senamit.nanodegreemovieapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailDescription extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieDetails>> {

    private Bundle bundle;
    private MovieDetails movieDetails;
    Target target;
    Button btnBookmark;
    private static final String LOG_TAG = MovieDetailDescription.class.getSimpleName();
    SQLiteDatabase db;
    Cursor cursor;
    MovieDBHelper movieDBHelper;
    Bitmap bitmapTest;
    String movieId;
    String stringUrl="https://api.themoviedb.org/3/movie/354912/reviews?api_key=f6fc8d8e4043fefdfe43c153dd429479&language=en-US";
    TextView txtMovieReview;
    Button btnReview;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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
         txtMovieReview = findViewById(R.id.txt_movie_review);
        btnBookmark = findViewById(R.id.btn_bookmark);
        btnReview = findViewById(R.id.btnReview);
        final ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_id);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        movieDetails = (MovieDetails) bundle.getParcelable("movieDesc");
        txtMovieName.setText(movieDetails.getMovieName());
        txtMovieReleaseDate.setText(movieDetails.getMovieReleaseDate());
        txtMovieDescr.setText(movieDetails.getMovieOverView());
        txtMovieRating.setText(movieDetails.getMovieRating());
        movieId = movieDetails.getMovieId();





        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                constraintLayout.setBackground(new BitmapDrawable(getResources(), bitmap));

//                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "amitt");

                bitmapTest=bitmap;

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

                ContentValues contentValues = new ContentValues();
                String movieName = txtMovieName.getText().toString();

                contentValues.put(WishListMovie.COLUMN_MOVIE_NAME, movieName);


                contentValues.put(WishListMovie.COLUMN_MOVIE_RELEASE_DATE,"1992");
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


        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringUrl =Uri.parse(MovieApiLinkCreator.MOVIE_DETAILS_JSON_DATA).buildUpon().appendPath(movieId).appendPath("reviews").appendQueryParameter(MovieApiLinkCreator.APIKEY,MovieApiLinkCreator.KEY).appendQueryParameter(MovieApiLinkCreator.LANGUAGE,MovieApiLinkCreator.LANGUAGEVALUE).build().toString();
                Log.i(LOG_TAG, "the movieId is "+movieId);
                Log.i(LOG_TAG, "the link of review is "+stringUrl);
                loaderMangerReview();
            }

            private void loaderMangerReview() {
                getLoaderManager().initLoader(36, savedInstanceState, MovieDetailDescription.this);
            }
        });

    }


    @Override
    public Loader<List<MovieDetails>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "inside oncreateloader of init loader");
        return new MovieReviewVideoLoader(this, stringUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieDetails>> loader, List<MovieDetails> data) {
        Log.i(LOG_TAG, "inside onLoadfinished method");
        int count = data.size();
        for (int i=0;i<count;i++){
            txtMovieReview.setText(data.get(i).getMovieReview());
        }
      txtMovieReview.setText(data.get(0).getMovieReview());
    }

    @Override
    public void onLoaderReset(Loader<List<MovieDetails>> loader) {

        txtMovieReview.setText(null);
    }
}
