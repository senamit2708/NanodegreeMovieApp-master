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
    private static final String OPERATION_QUERY_URL_EXTRA = "urlKey";
    private static final int QUERY_URL_ID= 10;
    RecyclerView recyclerView;
    MovieDetailAdapter movieDetailAdapter;
    private static int count = 1;
    Spinner spinner;
    Bundle savedInstanceState;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String stringTest = null;
    private String stringTestPref=null;
    private String KEY_URL="keyUrl";
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
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler);
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        Log.i(LOG_TAG, "inside stepuprecycler");
        makeOperationSearchQuery(stringTest);
//        count++;
//        stringTest = MovieApiLinkCreator.favrtMovieUrl1;
//        getLoaderManager().initLoader(0, savedInstanceState, MainActivity.this);
//        makeOperationSearchQuery(stringTest);
//        makeOperationSearchQuery(stringTest);



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
//        movieDetailAdapter = new MovieDetailAdapter(movieDetailsList, this);
//            recyclerView.setAdapter(movieDetailAdapter);
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
//                getLoaderManager().initLoader(1, savedInstanceState, MainActivity.this).forceLoad();



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
//                getLoaderManager().initLoader(1, savedInstanceState, MainActivity.this).forceLoad();
//                QUERY_URL_ID=1;
                makeOperationSearchQuery(stringTest);
                break;
            case "Top Rated":
                stringTest = MovieApiLinkCreator.favrtMovieUrl2;
                Log.i(LOG_TAG, "inside top raated");
//                getLoaderManager().initLoader(2, savedInstanceState, MainActivity.this).forceLoad();
//                QUERY_URL_ID=2;
                makeOperationSearchQuery(stringTest);
                break;
            case "Now Playing":
                stringTest = MovieApiLinkCreator.favrtMovieUrl3;
                Log.i(LOG_TAG, "inside now playing");
//                getLoaderManager().initLoader(3, savedInstanceState, MainActivity.this).forceLoad();
//                QUERY_URL_ID=3;
                makeOperationSearchQuery(stringTest);
                break;
            case "Upcoming":
                stringTest = MovieApiLinkCreator.favrtMovieUrl4;
//                getLoaderManager().initLoader(4, savedInstanceState, MainActivity.this).forceLoad();
//                QUERY_URL_ID=4;
                makeOperationSearchQuery(stringTest);
                break;
            case "BookMarked":
                Log.i(LOG_TAG, "inside the bookmarked");
                Intent bookmarkedIntent = new Intent(this, MovieBookmarkedList.class);
                startActivity(bookmarkedIntent);
                break;
            default:
                break;
        }
    }

//    private void makeOperationSearchQuery(String stringTest, int QUERY_URL_ID) {
//        getLoaderManager().initLoader(QUERY_URL_ID, savedInstanceState, MainActivity.this).forceLoad();
//    }

    private void makeOperationSearchQuery(String url){

        Bundle queryBundle = new Bundle();
        queryBundle.putString(OPERATION_QUERY_URL_EXTRA, url);
        LoaderManager loaderManager = getLoaderManager();
        Loader<String> loader = loaderManager.getLoader(QUERY_URL_ID);
        if (loader==null){
            Log.i(LOG_TAG, "inside the null loader, so init loader is called here");
            loaderManager.initLoader(QUERY_URL_ID, queryBundle, this);
        }else
        {
            Log.i(LOG_TAG, "inside not null loader, so restart loader is called here");

            loaderManager.restartLoader(QUERY_URL_ID, queryBundle, this);
        }

//                loaderManager.initLoader(QUERY_URL_ID, savedInstanceState, this);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_URL, stringTest);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getString(KEY_URL);
//        makeOperationSearchQuery(stringTest);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.create_new:
                bookmarkMovie();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void bookmarkMovie() {

        Intent intent = new Intent(MainActivity.this, MovieBookmarkedList.class);
        startActivity(intent);

    }
}
