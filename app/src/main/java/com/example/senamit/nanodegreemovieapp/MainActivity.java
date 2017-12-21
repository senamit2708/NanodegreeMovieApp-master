package com.example.senamit.nanodegreemovieapp;


import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieDetails>>, MovieDetailAdapter.ListItemClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView;
    MovieDetailAdapter movieDetailAdapter;
    private static int count = 1;
    Spinner spinner;
    Bundle savedInstanceState;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String stringTest = null;
    RecyclerView.LayoutManager mLayoutManager;
    private String PREF_FILE = "Option";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i(LOG_TAG, "inside oncreate");
        if (!CheckNetwork.isInternetAvailable(getApplicationContext())) {
            AlertDialogSettingFragment alertDialogSettingFragment = new AlertDialogSettingFragment();
            alertDialogSettingFragment.show(getSupportFragmentManager(), "dialog");
        } else {
            setupRecyclerView();
        }
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler);
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        Log.i(LOG_TAG, "inside stepuprecycler");
//        count++;
//        stringTest = MovieApiLinkCreator.favrtMovieUrl1;
        getLoaderManager().initLoader(0, savedInstanceState, MainActivity.this);



    }

    private void loadercalling() {
        Log.i(LOG_TAG, "inside loader calling" +count);

        getLoaderManager().initLoader(1, savedInstanceState, MainActivity.this).forceLoad();
        count++;
        Log.i(LOG_TAG, "inside loadercalling");
    }

    @Override
    public Loader<List<MovieDetails>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "the count is "+count);
        return new MovieDetailsLoader(this, stringTest);

    }

    @Override
    public void onLoadFinished(Loader<List<MovieDetails>> loader, List<MovieDetails> movieDetailsList) {
        if (movieDetailsList != null) {
            Log.i(LOG_TAG, "inside onloafinish");
            movieDetailAdapter = new MovieDetailAdapter(movieDetailsList, this);
            recyclerView.setAdapter(movieDetailAdapter);
        }
        Log.i(LOG_TAG, "inside on load fininshed");
//        count++;
    }

    @Override
    public void onLoaderReset(Loader<List<MovieDetails>> loader) {
//        movieDetailAdapter = new MovieDetailAdapter(new ArrayList<MovieDetails>(), this);
        recyclerView.setAdapter(null);
    }

    @Override
    public void onListItemClick(int clikcedItemIndex, MovieDetails movieDetailsList) {
        Intent intent = new Intent(MainActivity.this, MovieDetailDescription.class);
        intent.putExtra("movieDesc", movieDetailsList);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem spinnerItem = menu.findItem(R.id.menu_spinner);
        spinner = (Spinner) MenuItemCompat.getActionView(spinnerItem);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Log.i(LOG_TAG, "inside option menu");
        int value = sharedPreferences.getInt("positionKey", -1);
        if (value != -1) {
            spinner.setSelection(value);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String spinnerValue = spinner.getSelectedItem().toString();
                editor.putInt("positionKey", position);
                editor.commit();
                spinnerfun(spinnerValue);
//                    count++;
                Log.i(LOG_TAG, "inside onselected item spinner"+stringTest);
//                loadercalling();
//                getLoaderManager().initLoader(count, savedInstanceState, MainActivity.this);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return true;
    }

    private void spinnerfun(String spinnerValue) {
        switch (spinnerValue) {
            case "Popular":
                stringTest = MovieApiLinkCreator.favrtMovieUrl1;
                getLoaderManager().initLoader(1, savedInstanceState, MainActivity.this).forceLoad();
                break;
            case "Top Rated":
                stringTest = MovieApiLinkCreator.favrtMovieUrl2;
                getLoaderManager().initLoader(2, savedInstanceState, MainActivity.this).forceLoad();
                break;
            case "Now Playing":
                stringTest = MovieApiLinkCreator.favrtMovieUrl3;
                getLoaderManager().initLoader(3, savedInstanceState, MainActivity.this).forceLoad();
                break;
            case "Upcoming":
                stringTest = MovieApiLinkCreator.favrtMovieUrl4;
                getLoaderManager().initLoader(4, savedInstanceState, MainActivity.this).forceLoad();
                break;
            default:
                break;
        }
    }

}
