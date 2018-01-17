package com.example.senamit.nanodegreemovieapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
    String stringUrl1 = null;
    String stringUrl2 = null;
    TextView txtMovieReview;
    TextView txtMovieVideo;
    TextView txtVideoDesc;
    ListView listViewMovieVideo;
    MovieListViewVideoAdapter movieListViewVideoAdapter;
    Button btnReview;
    Button btnVideo;
    FloatingActionButton btnFloatingSave;
    private String KEY_URL = "keyUrl";
    private String KEY_URL_ARRAY="KeyUrlArray";
    private int LOADERIDREVIEW = 36;
    private int LOADERIDVIDEO = 46;
    private int loaderId = 0;
    private String youtubeKey = null;
    private String reviewValue=null;
    ArrayList<MovieDetails> arrayList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_description);
        if (savedInstanceState!=null){
            reviewValue=savedInstanceState.getString(KEY_URL);
            Log.i(LOG_TAG, "inside restore instance"+reviewValue);
//            getLoaderManager().initLoader(LOADERIDVIDEO, savedInstanceState, MovieDetailDescription.this);
            stringUrl2 = Uri.parse(MovieApiLinkCreator.MOVIE_DETAILS_JSON_DATA).buildUpon().appendPath(movieId).appendPath("videos").appendQueryParameter(MovieApiLinkCreator.APIKEY, MovieApiLinkCreator.KEY).appendQueryParameter(MovieApiLinkCreator.LANGUAGE, MovieApiLinkCreator.LANGUAGEVALUE).build().toString();

            getLoaderManager().initLoader(LOADERIDVIDEO, savedInstanceState, MovieDetailDescription.this);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        movieDBHelper = new MovieDBHelper(this);

        final TextView txtMovieName = findViewById(R.id.txt_movie_name);
        final TextView txtMovieReleaseDate = findViewById(R.id.txt_movieReleaseDate);
        final TextView txtMovieDescr = findViewById(R.id.txt_movie_descr);
        final TextView txtMovieRating = findViewById(R.id.movieRating);
        txtVideoDesc = findViewById(R.id.txtVideoDesc);
        listViewMovieVideo = findViewById(R.id.listViewVideo);
        txtMovieReview = findViewById(R.id.txt_movie_review);
//        txtMovieVideo = findViewById(R.id.txtVideo);
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
        final Context context = MovieDetailDescription.this;

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
                String movieReleaseDate = txtMovieReleaseDate.getText().toString();
                String movieRating = txtMovieRating.getText().toString();
                String movieOverview = txtMovieDescr.getText().toString();

                contentValues.put(WishListMovie.COLUMN_MOVIE_NAME, movieName);
                contentValues.put(WishListMovie.COLUMN_MOVIE_RELEASE_DATE, movieReleaseDate);
                contentValues.put(WishListMovie.COLUMN_MOVIE_RATING, movieRating);
                contentValues.put(WishListMovie.COLUMN_MOVIE_OVERVIEW, movieOverview);
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
                Log.i(LOG_TAG, "inside review button click");
                stringUrl1 = Uri.parse(MovieApiLinkCreator.MOVIE_DETAILS_JSON_DATA).buildUpon().appendPath(movieId).appendPath("reviews").appendQueryParameter(MovieApiLinkCreator.APIKEY, MovieApiLinkCreator.KEY).appendQueryParameter(MovieApiLinkCreator.LANGUAGE, MovieApiLinkCreator.LANGUAGEVALUE).build().toString();
                loaderMangerReview();
            }

            private void loaderMangerReview() {

                getLoaderManager().initLoader(LOADERIDREVIEW, savedInstanceState, MovieDetailDescription.this);
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringUrl2 = Uri.parse(MovieApiLinkCreator.MOVIE_DETAILS_JSON_DATA).buildUpon().appendPath(movieId).appendPath("videos").appendQueryParameter(MovieApiLinkCreator.APIKEY, MovieApiLinkCreator.KEY).appendQueryParameter(MovieApiLinkCreator.LANGUAGE, MovieApiLinkCreator.LANGUAGEVALUE).build().toString();
                Log.i(LOG_TAG, "the string url of videos is  " + stringUrl2);
                loaderManagerMovieVideo();
            }

            private void loaderManagerMovieVideo() {
                getLoaderManager().initLoader(LOADERIDVIDEO, savedInstanceState, MovieDetailDescription.this);

            }
        });

        listViewMovieVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(LOG_TAG, "inside onItem click of listview");
                String videoId= arrayList.get(position).getMovieVideo().toString();
                Log.i(LOG_TAG, "the video id is  "+videoId);
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
                Log.i(LOG_TAG, "the video id is  "+appIntent);
                try{
                    context.startActivity(appIntent);
                }catch (Exception e){
                    Log.i(LOG_TAG, "no video found");
                }
            }
        });


    }

    @Override
    public Loader<List<MovieDetails>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "inside oncreate loader");
        loaderId = id;
        if (loaderId == LOADERIDREVIEW) {
            return new MovieReviewVideoLoader(this, stringUrl1, id);
        }if (loaderId==LOADERIDVIDEO){
            return new MovieReviewVideoLoader(this, stringUrl2, id);
        }
       return null;
    }

    @Override
    public void onLoadFinished(Loader<List<MovieDetails>> loader, List<MovieDetails> data) {

        Log.i(LOG_TAG, "inside on load finished");
        if (data != null) {
            Log.i(LOG_TAG, "inside on load finished not null value");
            if (loaderId == LOADERIDVIDEO) {
                Log.i(LOG_TAG, "inside on load finished loadervideo");
                 arrayList = new ArrayList(data);
                movieListViewVideoAdapter = new MovieListViewVideoAdapter(this, arrayList);
                listViewMovieVideo.setAdapter(movieListViewVideoAdapter);
                loaderId=0;

            }
            if (loaderId == LOADERIDREVIEW) {
                int count = data.size();
                Log.i(LOG_TAG,"the count is "+count);
                for (int i=0;i<count;i++){
                    reviewValue=data.get(i).getMovieReview();
                }
                if (reviewValue!=null){
                    txtMovieReview.setText(reviewValue);
                }
                else {
                    txtMovieReview.setText("NO REVIEW AVAILABLE ");
                }

                loaderId=0;
            }

        }
// else {
//            if (loaderId == LOADERIDREVIEW) {
//                txtMovieReview.setText(null);
//            }
//            if (loaderId == LOADERIDVIDEO) {
//                txtMovieVideo.setText(null);
//            }
//
//        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieDetails>> loader) {
        Log.i(LOG_TAG, "inside reset loader");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_URL,reviewValue);
        outState.putInt(KEY_URL_ARRAY, 2);
    }

}
