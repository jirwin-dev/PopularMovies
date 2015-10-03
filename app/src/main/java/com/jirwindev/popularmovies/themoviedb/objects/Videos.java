package com.jirwindev.popularmovies.themoviedb.objects;

/**
 * Created by josh on 9/27/15.
 */
public class Videos {

	int           id;
	VideoResult[] results;

	public Videos() {
	}

	public Videos(int id, VideoResult[] results) {
		this.id = id;
		this.results = results;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public VideoResult[] getResults() {
		return results;
	}

	public void setResults(VideoResult[] results) {
		this.results = results;
	}
}
