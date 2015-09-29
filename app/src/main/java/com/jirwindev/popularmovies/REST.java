package com.jirwindev.popularmovies;

import com.jirwindev.popularmovies.themoviedb.objects.Configuration;
import com.jirwindev.popularmovies.themoviedb.objects.Discover;
import com.jirwindev.popularmovies.themoviedb.objects.Movie;
import com.jirwindev.popularmovies.themoviedb.objects.Reviews;
import com.jirwindev.popularmovies.themoviedb.objects.Videos;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by josh on 9/18/15.
 */
public class REST {
	protected RestAdapter   retrofit;
	protected TheMovieDBAPI api;
	static final String API_URL = "http://api.themoviedb.org/3/";


	public REST() {
		retrofit = new RestAdapter.Builder()
				.setEndpoint(API_URL)
				.build();

		api = retrofit.create(TheMovieDBAPI.class);
	}

	public void getConfiguration(String apiKey, Callback<Configuration> callback) {
		api.getConfiguration(apiKey, callback);
	}

	public void discoverMovies(String apiKey, String sort, Callback<Discover> callback) {
		api.discoverMovies(apiKey, sort, callback);
	}

	public void downloadPoster(String base, String size, String path,
							   com.squareup.okhttp.Callback callback) {
		com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
				.url(base + "/" + size + path)
				.build();

		OkHttpClient client = new OkHttpClient();

		client.newCall(request).enqueue(callback);
	}

	public void getMovie(String apiKey, int id, Callback<Movie> callback) {
		api.getMovie(apiKey, id, callback);
	}

	public void getMovieVideos(String apiKey, int id, Callback<Videos> callback) {
		api.getMovieVideos(apiKey, id, callback);
	}

	public void getMovieReviews(String apiKey, int id, Callback<Reviews> callback) {
		api.getMovieReviews(apiKey, id, callback);
	}
}
