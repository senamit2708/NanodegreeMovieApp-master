package com.example.senamit.nanodegreemovieapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senamit.nanodegreemovieapp.Data.MovieDBHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import com.example.senamit.nanodegreemovieapp.Data.MovieContract.*;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailDescription extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieDetails>> {

    private Bundle bundle;
    private MovieDetails movieDetails;
    Target target;
    private static final String LOG_TAG = MovieDetailDescription.class.getSimpleName();
    MovieDBHelper movieDBHelper;
    String movieId;
    String moviePoster;
    String stringUrl = null;
    TextView txtMovieReview;
    TextView txtMovieVideo;
    Button btnReview;
    Button btnVideo;
    FloatingActionButton btnFloatingSave;
    private String KEY_URL = "keyUrl";
    private int LOADERIDREVIEW = 36;
    private int LOADERIDVIDEO = 46;
    private int loaderId = 0;
    private String youtubeKey = null;
    private String reviewValue=null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_description);
        if (savedInstanceState!=null){
            reviewValue=savedInstanceState.getString(KEY_URL);
            Log.i(LOG_TAG, "inside restore instance"+reviewValue);
        }
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
        txtMovieVideo = findViewById(R.id.txtVideo);
        btnReview = findViewById(R.id.btnReview);
        btnVideo = findViewById(R.id.btnVideo);
        btnFloatingSave = (FloatingActionButton) findViewById(R.id.floatingbtnSave);
        final ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_id);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        movieDetails = (MovieDetails) bundle.getParcelable("movieDesc");
        txtMovieName.setText(movieDetails.getMovieName());
        txtMovieReleaseDate.setText(movieDetails.getMovieReleaseDate());
        txtMovieDescr.setText(movieDetails.getMovieOverView());
        txtMovieRating.setText(movieDetails.getMovieRating());
        Log.i(LOG_TAG, "the review is oncreate "+reviewValue);
        txtMovieReview.setText(reviewValue);
        movieId = movieDetails.getMovieId();
        moviePoster= movieDetails.getMovieImageUrl();

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


        btnFloatingSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                String movieName = txtMovieName.getText().toString();

                contentValues.put(WishListMovie.COLUMN_MOVIE_NAME, movieName);
                contentValues.put(WishListMovie.COLUMN_MOVIE_RELEASE_DATE, "1992");
                Log.i(LOG_TAG, "the poster url is "+moviePoster);
                contentValues.put(WishListMovie.COLUMN_MOVIE_POSTER,moviePoster);
                Uri uriId = getContentResolver().insert(WishListMovie.CONTENT_URI, contentValues);
                if (uriId != null) {
                    Toast.makeText(MovieDetailDescription.this, "successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MovieDetailDescription.this, "unsucessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringUrl = Uri.parse(MovieApiLinkCreator.MOVIE_DETAILS_JSON_DATA).buildUpon().appendPath(movieId).appendPath("reviews").appendQueryParameter(MovieApiLinkCreator.APIKEY, MovieApiLinkCreator.KEY).appendQueryParameter(MovieApiLinkCreator.LANGUAGE, MovieApiLinkCreator.LANGUAGEVALUE).build().toString();
                loaderMangerReview();
            }

            private void loaderMangerReview() {
                getLoaderManager().initLoader(LOADERIDREVIEW, savedInstanceState, MovieDetailDescription.this);
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringUrl = Uri.parse(MovieApiLinkCreator.MOVIE_DETAILS_JSON_DATA).buildUpon().appendPath(movieId).appendPath("videos").appendQueryParameter(MovieApiLinkCreator.APIKEY, MovieApiLinkCreator.KEY).appendQueryParameter(MovieApiLinkCreator.LANGUAGE, MovieApiLinkCreator.LANGUAGEVALUE).build().toString();
                Log.i(LOG_TAG, "the string url of videos is  " + stringUrl);
                loaderManagerMovieVideo();
            }

            private void loaderManagerMovieVideo() {
                getLoaderManager().initLoader(LOADERIDVIDEO, savedInstanceState, MovieDetailDescription.this);

            }
        });
    }

    @Override
    public Loader<List<MovieDetails>> onCreateLoader(int id, Bundle args) {
        loaderId = id;
        return new MovieReviewVideoLoader(this, stringUrl, id);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieDetails>> loader, List<MovieDetails> data) {

        if (data != null) {

            if (loaderId == LOADERIDVIDEO) {
//                txtMovieVideo.setText(data.get(0).getMovieReview());
                youtubeKey = data.get(0).getMovieVideo();
            }
            if (loaderId == LOADERIDREVIEW) {
                int count = data.size();
                reviewValue=data.get(0).getMovieReview();
                txtMovieReview.setText(reviewValue);
            }

        } else {
            if (loaderId == LOADERIDREVIEW) {
                txtMovieReview.setText(null);
            }
            if (loaderId == LOADERIDVIDEO) {
//                txtMovieVideo.setText(null);
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieDetails>> loader) {
        Log.i(LOG_TAG, "inside reset loader");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_URL,reviewValue);
    }

}
