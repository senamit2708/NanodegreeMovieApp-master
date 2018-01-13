package com.example.senamit.nanodegreemovieapp;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    public static ArrayList<MovieDetails> movieDetailsArrayList;

    public static URL createUrl(String stringUrl) throws MalformedURLException {
        if (stringUrl == null) {
            return null;
        }
        URL url = null;
        url = new URL(stringUrl);
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        if (url == null) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String jsonResponse = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.i(LOG_TAG, "the urlConnection is bad");
                return null;
            }
        } catch (IOException e) {
            Log.i(LOG_TAG, "the  connection is interuptrd " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<MovieDetails> extractFeaturesFromJSON(String jsonResponse) throws JSONException {

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        ArrayList<MovieDetails> movieDetailsArrayList = new ArrayList<MovieDetails>();
        String movieName = null;
        String releaseDate = null;
        String movieRating = null;
        String movieOverView = null;
        String movieId = null;

        JSONObject baseJsonObject = new JSONObject(jsonResponse);
        JSONArray resultJsonArray = baseJsonObject.optJSONArray("results");
        for (int i = 0; i < resultJsonArray.length(); i++) {
            JSONObject resultJsonObject = resultJsonArray.optJSONObject(i);
            movieId = resultJsonObject.optString("id");
            movieName = resultJsonObject.optString("title");
            releaseDate = resultJsonObject.optString("release_date");
            movieRating = resultJsonObject.optString("vote_average");
            movieOverView = resultJsonObject.optString("overview");
            String movieImage = resultJsonObject.optString("poster_path");
            StringBuilder imagePath = new StringBuilder();
            imagePath.append("http://image.tmdb.org/t/p/w500");
            imagePath.append(movieImage);
            Log.i(LOG_TAG, "movie id is "+movieId);
            movieDetailsArrayList.add(new MovieDetails(movieName, releaseDate, movieRating, movieOverView, imagePath.toString(),movieId));
        }
        return movieDetailsArrayList;
    }

    public static ArrayList<MovieDetails> fetchMovieRequest(String stringUrl) throws IOException, JSONException {
        URL url = createUrl(stringUrl);
        Log.i(LOG_TAG, "the url is "+url);
        String jsonResponsee = null;
        jsonResponsee = makeHttpRequest(url);
        movieDetailsArrayList = extractFeaturesFromJSON(jsonResponsee);
        Log.i(LOG_TAG, "inside the queryutils to retrive data");
        return movieDetailsArrayList;
    }

    public static ArrayList<MovieDetails>fetchMovieReview(String stringUrl) throws IOException, JSONException {
        URL url = createUrl(stringUrl);
        Log.i(LOG_TAG, "the url is "+url);
        String jsonResponsee = null;
        jsonResponsee = makeHttpRequest(url);
        movieDetailsArrayList=extractFeaturesJSONForReview(jsonResponsee);
        Log.i(LOG_TAG, "inside the fetchMovieReview to retrive data");
        return movieDetailsArrayList;

    }

    public static ArrayList<MovieDetails> extractFeaturesJSONForReview(String jsonResponse) throws JSONException {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        String movieReview =null;
        ArrayList<MovieDetails> movieDetailsArrayList = new ArrayList<MovieDetails>();
        JSONObject baseJsonObject = new JSONObject(jsonResponse);
        JSONArray resultJsonArray = baseJsonObject.optJSONArray("results");
        for (int i = 0; i < resultJsonArray.length(); i++) {
            JSONObject resultJsonObject = resultJsonArray.optJSONObject(i);

            movieReview=resultJsonObject.optString("content");
            Log.i(LOG_TAG, "inside extractFeaturesJSONForReview method");
            movieDetailsArrayList.add(new MovieDetails(movieReview));
        }
        return movieDetailsArrayList;

    }


}
