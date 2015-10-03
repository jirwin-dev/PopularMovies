package com.jirwindev.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.jirwindev.popularmovies.database.DatabaseHandler;
import com.jirwindev.popularmovies.themoviedb.objects.Configuration;
import com.jirwindev.popularmovies.themoviedb.objects.Discover;
import com.jirwindev.popularmovies.themoviedb.objects.Movie;
import com.jirwindev.popularmovies.themoviedb.objects.MoviePoster;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GridActivity2 extends Activity {

	//REST
	private REST    rest;
	private Movie[] movies;
	private int lastPage = 0;
	private File storageDir;

	//Elements
	private GridView    gridMovies;
	private GridAdapter adapter;

	private Configuration configuration;

	@Override
	protected void onCreate(Bundle inState) {
		super.onCreate(inState);
		setContentView(R.layout.activity_grid);

		//Get Elements
		gridMovies = (GridView) findViewById(R.id.moviesGridView);

		//Build REST
		rest = new REST();

		//Get configuration
		getConfiguration();

		//Get Storage DIR
		String root = Environment.getExternalStorageDirectory().toString();
		storageDir = new File(root + "/posters");
		if (!storageDir.exists())
			storageDir.mkdirs();

	}

	@Override
	protected void onResume() {
		super.onResume();
		gridMovies.invalidateViews();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.grid_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		return true;
	}

	private void getConfiguration() {
		rest.getConfiguration(TheMovieDBAPI.API_KEY, new Callback<Configuration>() {
			@Override
			public void success(Configuration c, Response response) {
				Log.e(getClass().getSimpleName(), "SUCCESS");

				configuration = c;

				getMovies();
			}

			@Override
			public void failure(RetrofitError error) {
//				Log.e(getClass().getSimpleName(), "TRY AGAIN");
//				getConfiguration();
				Log.e(getClass().getSimpleName(), "Could not get configuration, showing favorites");
				getOfflineMovies();
			}
		});
	}

	public void getMovies() {
		rest.discoverMovies(TheMovieDBAPI.API_KEY, "popularity.desc" /*TODO Conditional */,
				new Callback<Discover>() {
					@Override
					public void success(Discover discovery, Response response) {
						Log.e(getClass().getSimpleName(), "SUCCESS");

						//Results
						lastPage = discovery.getPage();
						movies = discovery.getResults();

						displayMovies();
					}

					@Override
					public void failure(RetrofitError error) {
						Log.e(getClass().getSimpleName(), "FAILURE");
						getOfflineMovies();
					}
				});
	}

	private void getOfflineMovies() {
		DatabaseHandler db = new DatabaseHandler(GridActivity2.this);
		List<Movie> movieList = db.getAllMovies();
		movies = new Movie[movieList.size()];
		movies = movieList.toArray(movies);
		db.close();

		displayMovies();
	}

	private void displayMovies() {
		//Grid Adapter

		//Get poster URIs
		for (int i = 0; i < movies.length; i++) {
			File poster = new File(storageDir, movies[i].getPosterPath().replace("/", ""));
			if (poster.exists()) {
				Log.i(getClass().getSimpleName(), "Poster for " + movies[i].getTitle() + " already exists");
				movies[i].setPoster(Uri.fromFile(poster));
			}
			else {
				final int position = i;
				rest.downloadPoster(
						configuration.getImages().getBase_url(),
						configuration.getImages().getPoster_sizes()[configuration.getImages()
								.getPoster_sizes().length / 2],
						movies[i].getPosterPath(),
						new com.squareup.okhttp.Callback() {

							@Override
							public void onFailure(Request request, IOException e) {
								Log.e(getClass().getSimpleName(), "FAILED");
								Log.e(getClass().getSimpleName(), request.toString());
								Log.e(getClass().getSimpleName(), request.urlString());
								e.printStackTrace();
							}

							@Override
							public void onResponse(com.squareup.okhttp.Response response) throws IOException {
								response.body().byteStream();

								//Get Storage Directory
								String fname = movies[position].getPosterPath()
										.replace("/", "");
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

										movies[position].setPoster(Uri.fromFile(file));
									}
									catch (Exception e) {
										e.printStackTrace();
									}
								}

							}
						});
			}

			adapter = new GridAdapter(getApplicationContext());
			new UpdateGridViewThread().run();
		}


	}

	private class UpdateGridViewThread extends Thread {

		@Override
		public void run() {
			GridActivity2.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					//Update UI
					Log.e(getClass().getSimpleName(), "RESETTING ADAPTER");
					adapter.notifyDataSetChanged();
					gridMovies.invalidateViews();
					gridMovies.setAdapter(adapter);
				}
			});
		}
	}

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
			DatabaseHandler db = new DatabaseHandler(GridActivity2.this);
			Movie favorite = db.getMovie(movies[position].getId());
			db.close();

			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(GridActivity2.this);
				convertView = inflater.inflate(R.layout.movie_poster, parent, false);
			}

			posterImageView = (ImageView) convertView.findViewById(R.id.poster);
			starImageView = (ImageView) convertView.findViewById(R.id.star);

			//Click listener
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, DetailsActivity.class);
					intent.putExtra(MoviePoster.ID, movies[position].getId());
					intent.putExtra(MoviePoster.TITLE, movies[position].getTitle());
					intent.putExtra(MoviePoster.POPULARITY, movies[position].getPopularity());
					intent.putExtra(MoviePoster.RELEASE_DATE, movies[position].getReleaseDate());
					intent.putExtra(MoviePoster.VOTE_AVERAGE, movies[position].getVoteAverage());
					intent.putExtra(MoviePoster.OVERVIEW, movies[position].getOverview());
					intent.putExtra(MoviePoster.POSTER_PATH, movies[position].getPoster().toString());
					startActivity(intent);
				}
			});

			//Set padding
			convertView.setLayoutParams(new GridView.LayoutParams(
					GridView.LayoutParams.MATCH_PARENT,
					500
			));

			//Show star if favorite
			starImageView = (ImageView) convertView.findViewById(R.id.star);
			if (favorite != null)
				starImageView.setVisibility(View.VISIBLE);
			else
				starImageView.setVisibility(View.GONE);

			//Set poster
			posterImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			posterImageView.setPadding(0, 0, 0, 0);
			posterImageView.setImageURI(movies[position].getPoster());

			return convertView;
		}
	}
}
