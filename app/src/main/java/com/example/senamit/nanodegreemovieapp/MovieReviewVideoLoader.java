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
    private int LOADERIDREVIEW = 36;
    private int LOADERIDVIDEO = 46;


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
            Log.i(LOG_TAG, "inside try block of review video");

            if (id==LOADERIDVIDEO){
                movieDetailsArrayList = QueryUtils.fetchMovieVideo(stringUrl);
                return movieDetailsArrayList;
            }
           if (id==LOADERIDREVIEW){
                movieDetailsArrayList = QueryUtils.fetchMovieReview(stringUrl);
               return movieDetailsArrayList;
            }




        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "inside doinbackground and in null return");
        return null;

    }
}
