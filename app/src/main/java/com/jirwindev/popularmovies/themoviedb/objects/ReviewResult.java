package com.jirwindev.popularmovies.themoviedb.objects;

/**
 * Created by josh on 10/3/15.
 */
public class ReviewResult {
	String id;
	int    movie_id;
	String author;
	String content;
	String url;

	public static final String ID       = "id";
	public static final String MOVIE_ID = "movie_id";
	public static final String AUTHOR   = "author";
	public static final String CONTENT  = "content";
	public static final String URL      = "url";

	public ReviewResult() {
	}

	public ReviewResult(String id, String author, String content, String url) {
		this.id = id;
		this.author = author;
		this.content = content;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMovieId() {
		return movie_id;
	}

	public void setMovieId(int movie_id) {
		this.movie_id = movie_id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

