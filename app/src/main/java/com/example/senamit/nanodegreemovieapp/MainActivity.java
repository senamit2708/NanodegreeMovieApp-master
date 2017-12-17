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
    private static int count = 0;
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
        getLoaderManager().initLoader(count, savedInstanceState, MainActivity.this);
    }

    @Override
    public Loader<List<MovieDetails>> onCreateLoader(int i, Bundle bundle) {
        return new MovieDetailsLoader(this, stringTest);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieDetails>> loader, List<MovieDetails> movieDetailsList) {
        if (movieDetailsList != null) {
            movieDetailAdapter = new MovieDetailAdapter(movieDetailsList, this);
            recyclerView.setAdapter(movieDetailAdapter);
        }
        count++;
    }

    @Override
    public void onLoaderReset(Loader<List<MovieDetails>> loader) {
        movieDetailAdapter = new MovieDetailAdapter(new ArrayList<MovieDetails>(), this);
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
                getLoaderManager().initLoader(count, savedInstanceState, MainActivity.this);
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
                break;
            case "Top Rated":
                stringTest = MovieApiLinkCreator.favrtMovieUrl2;
                break;
            case "Now Playing":
                stringTest = MovieApiLinkCreator.favrtMovieUrl3;
                break;
            case "Upcoming":
                stringTest = MovieApiLinkCreator.favrtMovieUrl4;
                break;
            default:
                break;
        }
    }

}
