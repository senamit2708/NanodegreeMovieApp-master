package com.example.senamit.nanodegreemovieapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by senamit on 13/1/18.
 */

public class MovieReviewVideoLoader extends AsyncTaskLoader<List<MovieDetails>> {

    private static final String LOG_TAG = MovieReviewVideoLoader.class.getSimpleName();
    private String stringUrl;
    ArrayList<MovieDetails> movieDetailsArrayList = new ArrayList<>();


    public MovieReviewVideoLoader(Context context) {
        super(context);
    }

    public MovieReviewVideoLoader(Context context, String stringUrl) {
        super(context);
        this.stringUrl = stringUrl;
    }

    @Override
    protected void onStartLoading() {
//        if(movieDetailsArrayList!=null){
//            super.onStartLoading();
//        }
//        else {
//            forceLoad();
//        }
        Log.i(LOG_TAG, "inside force load method");
        forceLoad();

    }

    @Override
    public List<MovieDetails> loadInBackground() {


        try {
            Log.i(LOG_TAG, "inside loadinbackground method");
            movieDetailsArrayList = QueryUtils.fetchMovieReview(stringUrl);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieDetailsArrayList;
    }
}
