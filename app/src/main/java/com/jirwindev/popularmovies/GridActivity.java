package com.jirwindev.popularmovies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.jirwindev.popularmovies.threads.MovieDBThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class GridActivity extends Activity {

	//Elements
	private GridView gridMovies;
	private Spinner  spinSortMethod;
	private int spinChangeCount = 0; //Stupid hack for a poor implementation on Google's part
	private int sortLastIndex   = 0;

	//Movie DB Data
	private MoviePoster[] moviePosters;
	private JSONObject    config;

	//Movie DB API
	final String MOVIEDB_CONFIG_URL = "http://api.themoviedb.org/3/configuration?api_key=";
	final String MOVIEDB_BASE_URL   = "http://api.themoviedb.org/3/discover/movie?";
	final String API_PARAM          = "api_key";
	final String API_KEY            = "16a2f4ed9f33bbef261ee48e3903443f";
	final String SORT_PARAM         = "sort_by";
	final String EXTRA_POSTERS      = "movie_posters";
	final String EXTRA_CONFIG       = "config";
	final String EXTRA_SORT         = "sort";

	@Override
	protected void onCreate(Bundle inState) {
		super.onCreate(inState);
		setContentView(R.layout.activity_grid);

		//Get Elements
		gridMovies = (GridView) findViewById(R.id.moviesGridView);
		spinSortMethod = (Spinner) findViewById(R.id.sortSpinner);

		//Assign Listeners
		spinSortMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (spinChangeCount > 0)
					discoverMovies(position);
				spinChangeCount++;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		//Set default values
		try {
			spinSortMethod.setSelection(inState.getInt(EXTRA_SORT), false);
		}
		catch (NullPointerException e) {
			Log.w(getClass().getSimpleName(), getString(R.string.error_sort));
		}

		try {
			config = new JSONObject(inState.getString(EXTRA_CONFIG));
		}
		catch (NullPointerException | JSONException e) {
			Log.w(getClass().getSimpleName(), getString(R.string.error_discover));
			getConfig();
		}

		if (config != null)
			try {
				moviePosters = (MoviePoster[]) inState.getParcelableArray(EXTRA_POSTERS);
				gridMovies.setAdapter(new GridAdapter(this));
			}
			catch (NullPointerException e) {
				Log.w(getClass().getSimpleName(), getString(R.string.error_config));
				discoverMovies(spinSortMethod.getSelectedItemPosition());
			}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (config != null)
			outState.putString(EXTRA_CONFIG, config.toString());
		outState.putParcelableArray(EXTRA_POSTERS, moviePosters);
		outState.putInt(EXTRA_SORT, spinSortMethod.getSelectedItemPosition());
		super.onSaveInstanceState(outState);
	}

	/**
	 * Creates the config uri and starts the call to the server
	 */
	private void getConfig() {
		Uri configUri = Uri.parse(MOVIEDB_CONFIG_URL).buildUpon()
				.appendQueryParameter(API_PARAM, API_KEY)
				.build();

		new ConfigThread(this).execute(configUri);
	}

	/**
	 * Gets TheMovieDB config
	 */
	private class ConfigThread extends MovieDBThread {

		public ConfigThread(Context context) {
			super(context);
			setupDialog(context, context.getString(R.string.config_thread_title));
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			super.onPostExecute(jsonObject);

			if (jsonObject == null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context)
						.setTitle(context.getString(R.string.config_thread_error_title))
						.setMessage(context.getString(R.string.config_thread_error_message))
						.setCancelable(false)
						.setNegativeButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								dialogInterface.dismiss();
							}
						});

				AlertDialog errorDialog = builder.create();
				errorDialog.show();
			}
			else {
				config = jsonObject;
				discoverMovies(spinSortMethod.getSelectedItemPosition());
			}
		}
	}

	/**
	 * Builds discovery uri and starts thread
	 */
	private void discoverMovies(int sortIndex) {
		if (config != null) {
			String sortMethod = getResources().getStringArray(R.array.sort_methods_api)[sortIndex];

			Uri discoverUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
					.appendQueryParameter(API_PARAM, API_KEY)
					.appendQueryParameter(SORT_PARAM, sortMethod)
					.build();

			new DiscoverThread(this).execute(discoverUri);
		}
		else
			getConfig();
	}

	/**
	 * Gets movie information from the movie db
	 */
	private class DiscoverThread extends MovieDBThread {

		public DiscoverThread(Context context) {
			super(context);
			setupDialog(context, context.getString(R.string.discover_thread_title));
		}

		@Override
		protected void onPostExecute(JSONObject discover) {
			super.onPostExecute(discover);

			if (discover == null) {
				spinChangeCount = 0;
				spinSortMethod.setSelection(sortLastIndex, false);

				AlertDialog.Builder builder = new AlertDialog.Builder(context)
						.setTitle(context.getString(R.string.discover_thread_error_title))
						.setMessage(context.getString(R.string.discover_thread_error_message))
						.setCancelable(false)
						.setNegativeButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								dialogInterface.dismiss();
							}
						});
				AlertDialog errorDialog = builder.create();
				errorDialog.show();
			}
			else {
				final String CONFIG_RESULTS = "results";
				final String CONFIG_IMAGES = "images";
				final String CONFIG_POSTER_SIZES = "poster_sizes";
				final String CONFIG_BASE_URL = "base_url";

				try {
					JSONArray results = discover.getJSONArray(CONFIG_RESULTS);

					//Get config information
					JSONObject imageConfig = config.getJSONObject(CONFIG_IMAGES);
					JSONArray imageSizes = imageConfig.getJSONArray(CONFIG_POSTER_SIZES);
					String posterSize = imageSizes.getString((int) imageSizes.length() / 2); //get middle size
					String baseUrl = imageConfig.getString(CONFIG_BASE_URL);

					moviePosters = new MoviePoster[results.length()];
					for (int i = 0; i < results.length(); i++) {
						JSONObject obj = results.getJSONObject(i);
						MoviePoster movie = new MoviePoster();
						movie.setId(obj.getInt(MoviePoster.ID));
						movie.setPath(baseUrl + posterSize + obj.getString(MoviePoster.POSTER_PATH));
						movie.setTitle(obj.getString(MoviePoster.TITLE));
						movie.setPlotSynopsis(obj.getString(MoviePoster.OVERVIEW));
						movie.setReleaseDate(obj.getString(MoviePoster.RELEASE_DATE));
						movie.setPopularity(obj.getDouble(MoviePoster.POPULARITY));
						movie.setVoteAverage(obj.getDouble(MoviePoster.VOTE_AVERAGE));
						moviePosters[i] = movie;
					}
				}
				catch (JSONException e) {
					e.printStackTrace();
				}

				//Grid Adapter
				GridAdapter adapter = new GridAdapter(context);
				gridMovies.setAdapter(adapter);

				//Get visible images
				for (int i = 0; i < moviePosters.length; i++) {
					if (moviePosters[i].getPoster() == null) {
						new PosterThread(i).execute(moviePosters[i].getPath());
						ImageView loadingImageView = new ImageView(context);
						loadingImageView.setImageResource(android.R.drawable.progress_indeterminate_horizontal);
					}
				}

				//Set last index
				sortLastIndex = spinSortMethod.getSelectedItemPosition();
			}
		}
	}

	/**
	 * Adapter for gridview to display poster image views
	 */
	public class GridAdapter extends BaseAdapter {

		private Context context;

		public GridAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return moviePosters.length;
		}

		@Override
		public Object getItem(int position) {
			MoviePoster poster = moviePosters[position];

			if (poster.getPoster() == null)
				new PosterThread(position).execute(moviePosters[position].getPath());
			return poster;
		}

		@Override
		public long getItemId(int position) {
			return moviePosters[position].getId();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(context);
				imageView.setLayoutParams(new GridView.LayoutParams(
						GridLayout.LayoutParams.FILL_PARENT,
						500
				));
				imageView.setPadding(8, 8, 8, 8);
				imageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context, DetailsActivity.class);
						intent.putExtra(MoviePoster.TITLE, moviePosters[position].getTitle());
						intent.putExtra(MoviePoster.POPULARITY, moviePosters[position].getPopularity());
						intent.putExtra(MoviePoster.RELEASE_DATE, moviePosters[position].getReleaseDate());
						intent.putExtra(MoviePoster.VOTE_AVERAGE, moviePosters[position].getVoteAverage());
						intent.putExtra(MoviePoster.OVERVIEW, moviePosters[position].getPlotSynopsis());
						intent.putExtra(MoviePoster.POSTER_PATH, moviePosters[position].getPoster().toString());
						startActivity(intent);
					}
				});
			}
			else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageURI(moviePosters[position].getPoster());
			return imageView;
		}
	}

	/**
	 * Downloads the movie poster asynchronously
	 */
	private class PosterThread extends AsyncTask<URL, Void, Uri> {

		private int position;

		public PosterThread(int position) {
			this.position = position;
		}

		@Override
		protected Uri doInBackground(URL... urls) {

			if (urls.length != 1)
				return null;
			URL url = urls[0];

			//Get Storage Directory
			String root = Environment.getExternalStorageDirectory().toString();
			File storageDir = new File(root + "/posters");
			storageDir.mkdirs();

			//Save poster to file
			String fname = moviePosters[position].getTitle() + ".png";
			File file = new File(storageDir, fname);

			if (!file.exists()) {
				try {
					//Download bitmap
					Bitmap poster = null;
					InputStream in = url.openStream();
					poster = BitmapFactory.decodeStream(in);

					//Save to file
					FileOutputStream out = new FileOutputStream(file);
					poster.compress(Bitmap.CompressFormat.PNG, 90, out);
					out.flush();
					out.close();
				}
				catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}

			return Uri.fromFile(file);
		}

		@Override
		protected void onPostExecute(Uri poster) {
			super.onPostExecute(poster);

			moviePosters[position].setPoster(poster);

			ImageView imageView = ((ImageView) gridMovies.getChildAt(position - gridMovies.getFirstVisiblePosition()));
			if (position <= gridMovies.getLastVisiblePosition() && imageView != null) {
				imageView.setImageURI(poster);
				imageView.invalidate();
			}
		}
	}
}
