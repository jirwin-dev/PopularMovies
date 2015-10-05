package com.jirwindev.popularmovies;

import com.jirwindev.popularmovies.themoviedb.objects.Configuration;
import com.jirwindev.popularmovies.themoviedb.objects.Discover;
import com.jirwindev.popularmovies.themoviedb.objects.Movie;
import com.jirwindev.popularmovies.themoviedb.objects.Reviews;
import com.jirwindev.popularmovies.themoviedb.objects.Videos;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by josh on 9/16/15.
 */
public interface TheMovieDBAPI {

	static final String API_KEY = "16a2f4ed9f33bbef261ee48e3903443f";

	@GET("/configuration")
	void getConfiguration(@Query("api_key") String api_key, Callback<Configuration> callback);

	@GET("/discover/movie")
	void discoverMovies(@Query("api_key") String api_key, @Query("sort_by") String sort,
	                    Callback<Discover> callback);

	@GET("/movie/{id}")
	void getMovie(@Query("api_key") String api_key, @Path("id") int id, Callback<Movie> callback);

	@GET("/movie/{id}/videos")
	void getMovieVideos(@Query("api_key") String api_key, @Path("id") int id,
	                    Callback<Videos> callback);

	@GET("/movie/{id}/reviews")
	void getMovieReviews(@Query("api_key") String api_key, @Path("id") int id,
	                     Callback<Reviews> callback);

}
