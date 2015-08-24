package com.jirwindev.popularmovies.threads;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

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

	protected ProgressDialog dialog;
	protected Context        context;

	public MovieDBThread(Context context) {
		super();
		this.context = context;
	}

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

		if (dialog != null && !dialog.isShowing())
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

			return new JSONObject(jsonString);
		}
		catch (JSONException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(JSONObject jsonObject) {
		super.onPostExecute(jsonObject);

		//Dismiss dialog
		try {
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
		}
		catch (Exception e) {
			//App redrawn
			e.printStackTrace();
		}
	}
}