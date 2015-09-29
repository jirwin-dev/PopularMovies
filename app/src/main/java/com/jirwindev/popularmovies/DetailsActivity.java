package com.jirwindev.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jirwindev.popularmovies.themoviedb.objects.Movie;
import com.jirwindev.popularmovies.themoviedb.objects.MoviePoster;
import com.jirwindev.popularmovies.themoviedb.objects.Reviews;
import com.jirwindev.popularmovies.themoviedb.objects.Videos;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailsActivity extends Activity {

	private REST rest;

	private LinearLayout trailers, reviews;
	private TextView overview;
	private int      id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		//Get Movie Data
		Bundle args = getIntent().getExtras();
		id = args.getInt(MoviePoster.ID);

		//Setup ActionBar
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(args.getString(MoviePoster.TITLE));

		//Get Elements
		ImageView posterImage = (ImageView) findViewById(R.id.posterImageView);
		TextView releaseDate = (TextView) findViewById(R.id.releaseDateTextView);
		trailers = (LinearLayout) findViewById(R.id.trailersLinearLayout);
		reviews = (LinearLayout) findViewById(R.id.reviewsLinearLayout);
		TextView voteAverage = (TextView) findViewById(R.id.voteAverageTextView);
		overview = (TextView) findViewById(R.id.overviewTextView);

		//Set defaults
		posterImage.setImageURI(Uri.parse(args.getString(MoviePoster.POSTER_PATH)));
		releaseDate.setText(args.getString(MoviePoster.RELEASE_DATE));
		voteAverage.setText(args.getDouble(MoviePoster.VOTE_AVERAGE) + "");

		//Build REST
		rest = new REST();
		getTrailers();
		getReviews();
		getMovie();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.details_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				return true;
			case R.id.favorite:
				item.setIcon(android.R.drawable.star_big_on);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		this.finish();
	}

	private void getMovie() {
		rest.getMovie(TheMovieDBAPI.API_KEY, id, new Callback<Movie>() {
			@Override
			public void success(Movie movie, Response response) {
				Log.i(getClass().getSimpleName(), "Got movie...");
				overview.setText(movie.getOverview());
			}

			@Override
			public void failure(RetrofitError error) {
				Log.e(getClass().getSimpleName(), "Could not get movie...");
				Log.e(getClass().getSimpleName(), error.toString());
				Log.e(getClass().getSimpleName(), error.getUrl());
			}
		});
	}

	private void getTrailers() {
		rest.getMovieVideos(TheMovieDBAPI.API_KEY, id,
				new Callback<Videos>() {
					@Override
					public void success(Videos v, Response response) {
						Log.i(getClass().getSimpleName(), "Got videos...");

						//Empty View
						trailers.removeAllViews();

						if (v.getResults().length == 0) {
							TextView noTrailers = new TextView(DetailsActivity.this);
							noTrailers.setText(getString(R.string.no_trailers));
							trailers.addView(noTrailers);
						}
						else {
							LayoutInflater inflater = getLayoutInflater();

							for (final Videos.Result trailer : v.getResults()) {
								View trailerLayout = inflater.inflate(R.layout.movie_trailer, null);

								//Get button and assign listener
								ImageButton trailerPlayButton = (ImageButton) trailerLayout.findViewById(R.id.playImageButton);
								trailerPlayButton.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View view) {
										//TODO conditional based on site
										startActivity(new Intent(Intent.ACTION_VIEW,
												Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()
												)));
									}
								});

								//Set title
								TextView titleTextView = (TextView) trailerLayout.findViewById(R.id.titleTextView);
								titleTextView.setText(trailer.getSite());

								//Add view
								trailers.addView(trailerLayout);
							}
						}
					}

					@Override
					public void failure(RetrofitError error) {
						Log.e(getClass().getSimpleName(), error.toString());
						Log.e(getClass().getSimpleName(), error.getUrl());

						trailers.removeAllViews();
						Button retry = new Button(DetailsActivity.this);
						retry.setText(getString(R.string.retry));
						retry.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								getTrailers();
							}
						});
						trailers.addView(retry);
					}
				});
	}

	private void getReviews() {
		rest.getMovieReviews(TheMovieDBAPI.API_KEY, id, new Callback<Reviews>() {
			@Override
			public void success(Reviews r, Response response) {
				Log.i(getClass().getSimpleName(), "Got reviews...");

				//Empty View
				reviews.removeAllViews();

				if (r.getResults().length == 0) {
					TextView noReviews = new TextView(DetailsActivity.this);
					noReviews.setText(getString(R.string.no_reviews));
					reviews.addView(noReviews);
				}
				else {
					LayoutInflater inflater = getLayoutInflater();

					for (final Reviews.Result review : r.getResults()) {
						View reviewLayout = inflater.inflate(R.layout.movie_review, null);

						//Set Author
						TextView author = (TextView) reviewLayout.findViewById(R.id.authorTextView);
						author.setText(review.getAuthor());

						//Set Review
						TextView reviewTextView = (TextView) reviewLayout.findViewById(R.id.reviewTextView);
						reviewTextView.setText(review.getContent());

						//Add view
						reviews.addView(reviewLayout);
					}
				}
			}

			@Override
			public void failure(RetrofitError error) {
				Log.e(getClass().getSimpleName(), error.toString());
				Log.e(getClass().getSimpleName(), error.getUrl());

				reviews.removeAllViews();
				Button retry = new Button(DetailsActivity.this);
				retry.setText(getString(R.string.retry));
				retry.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						getReviews();
					}
				});
				reviews.addView(retry);
			}
		});
	}
}
