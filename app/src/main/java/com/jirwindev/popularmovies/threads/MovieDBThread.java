package com.jirwindev.popularmovies.threads;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Josh on 8/20/2015.
 */
public class MovieDBThread extends AsyncTask<Uri, Void, JSONObject> {

    public static final String SORT_ASC = "", SORT_DESC = "";
    public static final String SORT_POPULARITY = "", SORT_RATING = "";

    final String MOVIEDB_CONFIG_URL = "http://api.themoviedb.org/3/configuration?api_key=";
    final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
    final String API_PARAM = "api_key";
    final String API_KEY = "REDACTED";

    private ProgressDialog dialog;

    protected void setupDialog(Context context, String title) {
        dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (dialog != null)
            dialog.show();
    }

    @Override
    protected JSONObject doInBackground(Uri... uris) {
        //Failsafe
        if (uris.length != 1)
            return null;

        //Get URI
        Uri uri = uris[0];

        //Create connection, reader, and empty string
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonString = null;

        try {
            URL url = new URL(uri.toString());

            Log.v(this.getClass().getSimpleName(), "Built URI " + uri.toString());

            //Connect to the server using a get request
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Connect to input stream
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
                return null;
            reader = new BufferedReader(new InputStreamReader(inputStream));

            //Read the results to a string
            String line;
            while ((line = reader.readLine()) != null)
                buffer.append(line);
            if (buffer.length() == 0)
                return null;
            jsonString = buffer.toString();

            //TODO Remove
            Log.e("RETURN", new JSONObject(jsonString).getJSONArray("results").getJSONObject(0).toString(4));

            return new JSONObject(jsonString);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        //Dismiss dialog
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}