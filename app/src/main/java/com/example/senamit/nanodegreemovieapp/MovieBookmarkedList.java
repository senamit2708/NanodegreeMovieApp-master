package com.example.senamit.nanodegreemovieapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.ContentUris;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.senamit.nanodegreemovieapp.Data.MovieContract;
import com.example.senamit.nanodegreemovieapp.Data.MovieContract.*;

public class MovieBookmarkedList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, CustomCursorBookmarkMovieAdapter.ListItemClickListener{

    public static final String LOG_TAG = MovieBookmarkedList.class.getSimpleName();
    RecyclerView mRecyclerView;
    CustomCursorBookmarkMovieAdapter mAdapter;
    int LOADERID = 29;
    private int recyclerNumColumn = 2;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_bookmarked_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewBookmarked);
        mLayoutManager= new GridLayoutManager(this, recyclerNumColumn);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        mRecyclerView.setAdapter(mAdapter);
        getLoaderManager().initLoader(LOADERID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mMovieCursor = null;

            @Override
            protected void onStartLoading() {
                if (mMovieCursor != null) {
                    deliverResult(mMovieCursor);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(WishListMovie.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    Log.e(LOG_TAG, "failed to load data");
                }

                return null;
            }

            @Override
            public void deliverResult(Cursor data) {
                mMovieCursor = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mAdapter = new CustomCursorBookmarkMovieAdapter(this, this);
        mAdapter.swapCursor(data);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(int clikcedItemIndex, int id) {
        Log.i(LOG_TAG, "isnide the main of onlistitemclick listener");
        Uri currentProductUri = ContentUris.withAppendedId(WishListMovie.CONTENT_URI, id);
        Intent intent = new Intent(this, MovieBookMarkedDescription.class);
        intent.setData(currentProductUri);
        startActivity(intent);
    }
}
