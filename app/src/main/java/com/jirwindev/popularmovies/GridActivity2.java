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

import com.jirwindev.popularmovies.themoviedb.objects.Configuration;
import com.jirwindev.popularmovies.themoviedb.objects.Discover;
import com.jirwindev.popularmovies.themoviedb.objects.MoviePoster;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GridActivity2 extends Activity {

	//REST
	private REST             rest;
	private Discover.Movie[] movies;
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
				Log.e(getClass().getSimpleName(), "TRY AGAIN");
				getConfiguration();
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

						//Grid Adapter
						adapter = new GridAdapter(getApplicationContext());
						gridMovies.setAdapter(adapter);

						//Get poster URIs
						for (int i = 0; i < movies.length; i++) {
							File poster = new File(storageDir, movies[i].getPoster_path().replace("/", ""));
							if (poster.exists())
								movies[i].setPoster(Uri.fromFile(poster));
							else {
								final int position = i;
								rest.downloadPoster(
										configuration.getImages().getBase_url(),
										configuration.getImages().getPoster_sizes()[configuration.getImages()
												.getPoster_sizes().length / 2],
										movies[i].getPoster_path(),
										new com.squareup.okhttp.Callback() {

											@Override
											public void onFailure(Request request, IOException e) {

											}

											@Override
											public void onResponse(com.squareup.okhttp.Response response) throws IOException {
												response.body().byteStream();

												//Get Storage Directory
												String fname = movies[position].getPoster_path()
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

												//Update UI
												new UpdateGridViewThread().run();
											}
										});
							}
						}


					}

					@Override
					public void failure(RetrofitError error) {
						Log.e(getClass().getSimpleName(), "FAILURE");
					}
				});
	}

	private class UpdateGridViewThread extends Thread {

		@Override
		public void run() {
			GridActivity2.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					adapter.notifyDataSetChanged();
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

			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(GridActivity2.this);
				convertView = inflater.inflate(R.layout.movie_poster, parent, false);

				posterImageView = (ImageView) convertView.findViewById(R.id.poster);
				starImageView = (ImageView) convertView.findViewById(R.id.star);

				posterImageView = new ImageView(context);

				convertView.setLayoutParams(new GridView.LayoutParams(
						GridView.LayoutParams.MATCH_PARENT,
						500
				));
				posterImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				posterImageView.setPadding(0, 0, 0, 0);
				if (movies[position].getPoster() != null)
					posterImageView.setImageURI(movies[position].getPoster());
				posterImageView.invalidate();

				convertView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context, DetailsActivity.class);
						intent.putExtra(MoviePoster.ID, movies[position].getId());
						intent.putExtra(MoviePoster.TITLE, movies[position].getTitle());
						intent.putExtra(MoviePoster.POPULARITY, movies[position].getPopularity());
						intent.putExtra(MoviePoster.RELEASE_DATE, movies[position].getRelease_date());
						intent.putExtra(MoviePoster.VOTE_AVERAGE, movies[position].getVote_average());
						intent.putExtra(MoviePoster.OVERVIEW, movies[position].getOverview());
						intent.putExtra(MoviePoster.POSTER_PATH, movies[position].getPoster().toString());
						startActivity(intent);
					}
				});
			}
			else {
				View layout = convertView;

				posterImageView = (ImageView) layout.findViewById(R.id.poster);
				starImageView = (ImageView) layout.findViewById(R.id.star);
			}

			posterImageView.setImageURI(movies[position].getPoster());

			return convertView;
		}
	}
}
