package com.jirwindev.popularmovies;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jirwindev.popularmovies.database.DatabaseHandler;
import com.jirwindev.popularmovies.themoviedb.objects.Configuration;
import com.jirwindev.popularmovies.themoviedb.objects.Discover;
import com.jirwindev.popularmovies.themoviedb.objects.Movie;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GridFragment extends Fragment {

	private static final String STATE_CONFIG          = "configuration";
	private static final String STATE_MOVIES          = "movies";
	private static final String STATE_ERROR           = "error";
	private static final String STATE_SORT_POPULARITY = "popularity";
	private static final String STATE_SORT_RATING     = "rating";

	//REST
	private REST    rest;
	private Movie[] movies;
	private File    storageDir;

	//Elements
	private GridView    gridMovies;
	private GridAdapter adapter;
	private TextView    tvError;
	private Menu        menu;
	private boolean     sortPopularity, sortRating;

	private Configuration configuration;

	private Communicator comm;

	public GridFragment() {
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle inState) {

		//Communicator
		comm = (Communicator) getActivity();

		//Root View
		View rootView = inflater.inflate(R.layout.fragment_grid, container, false);

		//Get Elements
		gridMovies = (GridView) rootView.findViewById(R.id.moviesGridView);
		tvError = (TextView) rootView.findViewById(R.id.errorTextView);

		//Get sort
		try {
			sortPopularity = inState.getBoolean(STATE_SORT_POPULARITY);
		}
		catch (Exception e) {
			sortPopularity = true;
		}
		try {
			sortRating = inState.getBoolean(STATE_SORT_RATING);
		}
		catch (Exception e) {
			sortRating = false;
		}

		//Set Listners
		tvError.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (configuration == null)
					getConfiguration();
				else {
					MenuItem sortPopular = menu.findItem(R.id.sort_popular);
					MenuItem sortRating = menu.findItem(R.id.sort_rating);

					if (sortRating.isChecked())
						getMovies(getString(R.string.sort_rating_param));
					if (sortPopular.isChecked())
						getMovies(getString(R.string.sort_popular_param));
				}
			}
		});

		//Build REST
		rest = new REST();

		//Get configuration
		try {
			configuration = (Configuration) inState.getParcelable(STATE_CONFIG);
			movies = (Movie[]) inState.getParcelableArray(STATE_MOVIES);
			displayMovies();
		}
		catch (Exception e) {
			getConfiguration();
		}

		//Get Error
		try {
			tvError.setText(inState.getString(STATE_ERROR));
		}
		catch (Exception e) {
			tvError.setText("");
		}

		//Get Storage DIR
		String root = Environment.getExternalStorageDirectory().toString();
		storageDir = new File(root + "/posters");
		if (!storageDir.exists())
			storageDir.mkdirs();

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(STATE_CONFIG, configuration);
		outState.putParcelableArray(STATE_MOVIES, movies);
		outState.putString(STATE_ERROR, tvError.getText().toString());
	}

	/**
	 * Gets TheMovieDB configuration
	 */
	private void getConfiguration() {
		rest.getConfiguration(TheMovieDBAPI.API_KEY, new Callback<Configuration>() {
			@Override
			public void success(Configuration c, Response response) {
				//Store
				configuration = c;

				//Get movies
				getMovies(comm.getSort());
			}

			@Override
			public void failure(RetrofitError error) {
				Log.e(getClass().getSimpleName(), error.toString());
				Log.e(getClass().getSimpleName(), error.getUrl());

				tvError.setText(getString(R.string.error));
				movies = new Movie[0];
				displayMovies();
			}
		});
	}

	/**
	 * Gets the movies base ond your sort parameter
	 */
	public void getMovies(String sortMethod) {
		rest.discoverMovies(TheMovieDBAPI.API_KEY, sortMethod,
				new Callback<Discover>() {
					@Override
					public void success(Discover discovery, Response response) {
						//Store
						movies = discovery.getResults();

						//Show em
						displayMovies();
					}

					@Override
					public void failure(RetrofitError error) {
						Log.e(getClass().getSimpleName(), error.toString());
						Log.e(getClass().getSimpleName(), error.getUrl());

						tvError.setText(getString(R.string.error));
						movies = new Movie[0];
						displayMovies();
					}
				});
	}

	/**
	 * Gets your favorites and resets the adapter
	 */
	public void getFavorites() {
		DatabaseHandler db = new DatabaseHandler(getActivity());
		List<Movie> movieList = db.getAllMovies();
		movies = new Movie[movieList.size()];
		movies = movieList.toArray(movies);
		db.close();

		//Remove Error Message
		tvError.setText("");

		//Show em
		displayMovies();
	}

	/**
	 * Downloads or sets all posters, and then sets the adapter
	 */
	private void displayMovies() {
		//Get poster URIs
		for (int i = 0; i < movies.length; i++) {

			//Supposed poster file
			if (movies[i].getPosterPath() != null) {
				File poster = new File(storageDir, movies[i].getPosterPath().replace("/", ""));
				if (poster.exists()) {
					movies[i].setPoster(Uri.fromFile(poster));
				}
				else {
					downloadPoster(i);
				}
			}
		}

		//Clear error
		if (movies.length > 0)
			tvError.setText("");

		//Update adapter
		adapter = new GridAdapter(getActivity());
		gridMovies.setAdapter(adapter);
	}

	/**
	 * Attempts to download the poster for a movie
	 *
	 * @param position
	 */
	private void downloadPoster(final int position) {
		rest.downloadPoster(
				configuration.getImages().getBase_url(),
				configuration.getImages().getPoster_sizes()[configuration.getImages().getPoster_sizes().length / 2],
				movies[position].getPosterPath(),
				new com.squareup.okhttp.Callback() {

					@Override
					public void onFailure(Request request, IOException e) {
					}

					@Override
					public void onResponse(com.squareup.okhttp.Response response) throws IOException {
						response.body().byteStream();

						//Get Storage Directory
						String fname = movies[position].getPosterPath().replace("/", "");
						File file = new File(storageDir, fname);

						if (!file.exists()) {
							try {
								//Download bitmap
								Bitmap poster = null;
								InputStream in = response.body().byteStream();
								poster = BitmapFactory.decodeStream(in);

								//Save to file
								FileOutputStream out = new FileOutputStream(file);
								poster.compress(Bitmap.CompressFormat.JPEG, 90, out);
								out.flush();
								out.close();

								//Set poster
								movies[position].setPoster(Uri.fromFile(file));
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}

						new UpdateGridViewThread().run();
					}
				});
	}

	/**
	 * Updates the grid view
	 */
	private class UpdateGridViewThread extends Thread {

		@Override
		public void run() {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					adapter.notifyDataSetChanged();
					gridMovies.invalidateViews();
				}
			});
		}
	}

	/**
	 * Adapter for the GridView that shows movie posters
	 */
	class GridAdapter extends BaseAdapter {

		private Context context;

		public GridAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return movies.length;
		}

		@Override
		public Object getItem(int position) {
			return movies[position];
		}

		@Override
		public long getItemId(int position) {
			return movies[position].getId();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ImageView posterImageView, starImageView;

			//Get favorites
			DatabaseHandler db = new DatabaseHandler(getActivity());
			Movie favorite = db.getMovie(movies[position].getId());
			db.close();

			//Get item layout
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(getActivity());
				convertView = inflater.inflate(R.layout.movie_poster, parent, false);
			}

			//Get elements
			posterImageView = (ImageView) convertView.findViewById(R.id.poster);
			starImageView = (ImageView) convertView.findViewById(R.id.star);

			//Click listener
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					comm.showDetails(movies[position]);
				}
			});

			//Show star if favorite
			starImageView = (ImageView) convertView.findViewById(R.id.star);
			if (favorite != null)
				starImageView.setVisibility(View.VISIBLE);
			else
				starImageView.setVisibility(View.GONE);

			//Set poster
			posterImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			posterImageView.setPadding(0, 0, 0, 0);
			if (movies[position].getPoster() != null)
				posterImageView.setImageURI(movies[position].getPoster());

			return convertView;
		}
	}
}
