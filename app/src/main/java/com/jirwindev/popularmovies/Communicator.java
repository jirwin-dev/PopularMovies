package com.jirwindev.popularmovies;

import com.jirwindev.popularmovies.themoviedb.objects.Movie;

/**
 * Created by josh on 10/5/15.
 */
public interface Communicator {

	public void showDetails(Movie movie);

	public String getSort();

}
