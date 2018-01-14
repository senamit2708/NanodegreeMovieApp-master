package com.example.senamit.nanodegreemovieapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MovieReviewVideoLoader extends AsyncTaskLoader<List<MovieDetails>> {

    private static final String LOG_TAG = MovieReviewVideoLoader.class.getSimpleName();
    private String stringUrl;
    ArrayList<MovieDetails> movieDetailsArrayList = new ArrayList<>();
    int id=0;


    public MovieReviewVideoLoader(Context context) {
        super(context);
    }

    public MovieReviewVideoLoader(Context context, String stringUrl, int id) {
        super(context);
        this.stringUrl = stringUrl;
        this.id=id;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieDetails> loadInBackground() {

        try {
            if (id==36){
                movieDetailsArrayList = QueryUtils.fetchMovieReview(stringUrl);
            }
            if (id==46){
                movieDetailsArrayList = QueryUtils.fetchMovieVideo(stringUrl);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieDetailsArrayList;
    }
}
