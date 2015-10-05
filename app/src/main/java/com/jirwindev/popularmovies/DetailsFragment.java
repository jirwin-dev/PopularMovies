package com.jirwindev.popularmovies;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jirwindev.popularmovies.database.DatabaseHandler;
import com.jirwindev.popularmovies.themoviedb.objects.Movie;
import com.jirwindev.popularmovies.themoviedb.objects.ReviewResult;
import com.jirwindev.popularmovies.themoviedb.objects.Reviews;
import com.jirwindev.popularmovies.themoviedb.objects.VideoResult;
import com.jirwindev.popularmovies.themoviedb.objects.Videos;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailsFragment extends Fragment {

	private static final String STATE_MOVIE        = "movie";
	private static final String STATE_VIDEOS       = "videos";
	private static final String STATE_REVIEWS      = "reviews";
	private static final String STATE_ID           = "id";
	private static final String STATE_POSTER_PATH  = "poster_path";
	private static final String STATE_RELEASE_DATE = "release_date";
	private static final String STATE_VOTE_AVERAGE = "vote_average";

	private REST         rest;
	private LinearLayout trailersLayout, reviewsLayout;
	private ImageView posterImage;
	private Uri       posterImageUri;
	private TextView  title, overview, year, duration, voteAverage, releaseDate;
	private Button  btnFavorite;
	private int     id;
	private Movie   movie;
	private Reviews reviews;
	private Videos  videos;
	private Toast   toast;

	public DetailsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle inState) {

		View rootView = inflater.inflate(R.layout.fragment_details, container, false);

		//Setup Toast
		toast = new Toast(getActivity());

		//Get Elements
		title = (TextView) rootView.findViewById(R.id.titleTextView);
		posterImage = (ImageView) rootView.findViewById(R.id.posterImageView);
		releaseDate = (TextView) rootView.findViewById(R.id.releaseDateTextView);
		btnFavorite = (Button) rootView.findViewById(R.id.favoriteButton);
		trailersLayout = (LinearLayout) rootView.findViewById(R.id.trailersLinearLayout);
		reviewsLayout = (LinearLayout) rootView.findViewById(R.id.reviewsLinearLayout);
		voteAverage = (TextView) rootView.findViewById(R.id.voteAverageTextView);
		overview = (TextView) rootView.findViewById(R.id.overviewTextView);
		year = (TextView) rootView.findViewById(R.id.yearTextView);
		duration = (TextView) rootView.findViewById(R.id.durationTextView);

		//Get Movie Data
		Bundle args = getArguments();
		if (args != null) {
			id = args.getInt(Movie.ID);
			if (args.getString(Movie.POSTER_PATH) != null)
				posterImageUri = Uri.parse(args.getString(Movie.POSTER_PATH));
			releaseDate.setText(args.getString(Movie.RELEASE_DATE));
			voteAverage.setText(args.getDouble(Movie.VOTE_AVERAGE) + "");
		}

		//Build REST
		rest = new REST();

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle inState) {
		super.onActivityCreated(inState);

		if (inState != null) {
			//Josh used Full Restore on Details Fragment
			id = inState.getInt(STATE_ID);
			posterImageUri = Uri.parse(inState.getString(STATE_POSTER_PATH));
			voteAverage.setText(inState.getString(STATE_VOTE_AVERAGE));
			releaseDate.setText(inState.getString(STATE_RELEASE_DATE));
		}

		//Set poster image
		if (posterImageUri == null)
			posterImage.setImageResource(R.drawable.no_poster);
		else
			posterImage.setImageURI(posterImageUri);

		//Get Trailers
		try {
			videos = (Videos) inState.getParcelable(STATE_VIDEOS);
			trailersLayout.removeAllViews();
			displayVideos();
		}
		catch (Exception e) {
			getTrailers();
		}

		//Get Reviews
		try {
			reviews = (Reviews) inState.getParcelable(STATE_REVIEWS);
			reviewsLayout.removeAllViews();
			displayReviews();
		}
		catch (Exception e) {
			getReviews();
		}

		//Get Movies
		try {
			movie = (Movie) inState.getParcelable(STATE_MOVIE);
			displayMovies();
		}
		catch (Exception e) {
			getMovie();
		}

		//Set Favorite
		DatabaseHandler db = new DatabaseHandler(getActivity());
		if (db.getMovie(id) != null)
			btnFavorite.setText(getString(R.string.unfavoriteButton));
		else
			btnFavorite.setText(getString(R.string.favoriteButton));
		db.close();

		//Set Listeners
		btnFavorite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DatabaseHandler db = new DatabaseHandler(getActivity());

				if (btnFavorite.getText().equals(getString(R.string.favoriteButton))) {

					if (movie == null) {
						toast.cancel();
						toast.makeText(getActivity(), getString(R.string.offline_favorite),
								Toast.LENGTH_SHORT).show();
					}
					else {
						btnFavorite.setText(getString(R.string.unfavoriteButton));
						db.addOrUpdateMovie(movie);
						db.addOrUpdateReviews(reviews);
						db.addOrUpdateVideos(videos);
					}
				}
				else {
					btnFavorite.setText(getString(R.string.favoriteButton));
					db.deleteMovie(id);
					db.deleteReviewsByMovie(id);
					db.deleteVideosByMovie(id);
				}

				db.close();
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(STATE_MOVIE, movie);
		outState.putParcelable(STATE_REVIEWS, reviews);
		outState.putParcelable(STATE_VIDEOS, videos);
		outState.putInt(STATE_ID, id);
		outState.putString(STATE_POSTER_PATH, posterImageUri.toString());
		outState.putString(STATE_RELEASE_DATE, releaseDate.getText().toString());
		outState.putString(STATE_VOTE_AVERAGE, voteAverage.getText().toString());

		super.onSaveInstanceState(outState);
	}

	/**
	 * Gets a movie
	 */
	private void getMovie() {
		rest.getMovie(TheMovieDBAPI.API_KEY, id, new Callback<Movie>() {
			@Override
			public void success(Movie m, Response response) {
				movie = m;
				displayMovies();
			}

			@Override
			public void failure(RetrofitError error) {
				Log.e(getClass().getSimpleName(), error.toString());
				Log.e(getClass().getSimpleName(), error.getUrl());

				DatabaseHandler db = new DatabaseHandler(getActivity());
				movie = db.getMovie(id);
				db.close();

				displayMovies();
			}
		});
	}

	/**
	 * Displays the movies
	 */
	private void displayMovies() {
		if (movie == null) {
			overview.setText(getString(R.string.overview_error));
		}
		else {
			title.setText(movie.getTitle());
			overview.setText(movie.getOverview());
			duration.setText(movie.getRuntime() + " min.");
			year.setText(movie.getReleaseDate().substring(0, 4));
		}
	}

	/**
	 * Gets the movies trailers
	 */
	private void getTrailers() {
		rest.getMovieVideos(TheMovieDBAPI.API_KEY, id,
				new Callback<Videos>() {
					@Override
					public void success(Videos v, Response response) {
						//Store
						videos = v;

						//Empty View
						trailersLayout.removeAllViews();

						//Show em
						displayVideos();
					}

					@Override
					public void failure(RetrofitError error) {
						Log.e(getClass().getSimpleName(), error.toString());
						Log.e(getClass().getSimpleName(), error.getUrl());

						//Clear all views
						trailersLayout.removeAllViews();

						//Check database
						DatabaseHandler db = new DatabaseHandler(getActivity());
						List<VideoResult> videoResults = db.getVideosByMovie(id);
						db.close();

						//Show videos from db
						videos = new Videos();
						videos.setId(id);
						VideoResult[] arrVideoResults = new VideoResult[videoResults.size()];
						arrVideoResults = videoResults.toArray(arrVideoResults);
						videos.setResults(arrVideoResults);

						//Show em
						displayVideos();
					}
				}

		);
	}

	/**
	 * Displays the movies videos
	 */
	private void displayVideos() {

		if (videos.getResults().length == 0) {
			TextView noTrailers = new TextView(getActivity());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT,
					Gravity.CENTER);
			noTrailers.setGravity(Gravity.CENTER);

			noTrailers.setLayoutParams(params);
			noTrailers.setText(getString(R.string.no_trailers));
			trailersLayout.addView(noTrailers);
		}
		else {
			LayoutInflater inflater = getActivity().getLayoutInflater();

			for (final VideoResult trailer : videos.getResults()) {
				View trailerLayout = inflater.inflate(R.layout.movie_trailer, null);

				//Get button and assign listener
				ImageButton trailerPlayButton = (ImageButton) trailerLayout.findViewById(R.id.playImageButton);
				trailerPlayButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						//TODO conditional based on site
						startActivity(new Intent(Intent.ACTION_VIEW,
								Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey())));
					}
				});

				//Set title
				TextView titleTextView = (TextView) trailerLayout.findViewById(R.id.titleTextView);
				titleTextView.setText(trailer.getSite());

				//Add view
				trailersLayout.addView(trailerLayout);
			}
		}
	}

	/**
	 * Gets the movies reviews
	 */
	private void getReviews() {
		rest.getMovieReviews(TheMovieDBAPI.API_KEY, id, new Callback<Reviews>() {
			@Override
			public void success(Reviews r, Response response) {
				//Store
				reviews = r;

				//Empty layout
				reviewsLayout.removeAllViews();

				//Show em
				displayReviews();
			}

			@Override
			public void failure(RetrofitError error) {
				Log.e(getClass().getSimpleName(), error.toString());
				Log.e(getClass().getSimpleName(), error.getUrl());

				//Empty layout
				reviewsLayout.removeAllViews();

				//Check DB for reviews
				DatabaseHandler db = new DatabaseHandler(getActivity());
				List<ReviewResult> reviewResults = db.getReviewsByMovie(id);
				db.close();

				//Repopulate Reviews
				reviews = new Reviews();
				reviews.setId(id);
				ReviewResult[] arrReviewResults = new ReviewResult[reviewResults.size()];
				arrReviewResults = reviewResults.toArray(arrReviewResults);
				reviews.setResults(arrReviewResults);

				//Show em
				displayReviews();

			}
		});
	}

	/**
	 * Displays the movies reviews
	 */
	private void displayReviews() {
		if (reviews.getResults().length == 0) {
			TextView noReviews = new TextView(getActivity());
			noReviews.setText(getString(R.string.no_reviews));
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT,
					Gravity.CENTER);
			noReviews.setLayoutParams(params);
			noReviews.setGravity(Gravity.CENTER);
			reviewsLayout.addView(noReviews);
		}
		else {
			LayoutInflater inflater = getActivity().getLayoutInflater();

			for (final ReviewResult review : reviews.getResults()) {
				View reviewLayout = inflater.inflate(R.layout.movie_review, null);

				//Set Author
				TextView author = (TextView) reviewLayout.findViewById(R.id.authorTextView);
				author.setText(review.getAuthor());

				//Set Review
				TextView reviewTextView = (TextView) reviewLayout.findViewById(R.id.reviewTextView);
				reviewTextView.setText(review.getContent());

				//Add view
				reviewsLayout.addView(reviewLayout);
			}
		}
	}

}
