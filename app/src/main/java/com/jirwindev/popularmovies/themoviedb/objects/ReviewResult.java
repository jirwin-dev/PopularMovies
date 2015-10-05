package com.jirwindev.popularmovies.themoviedb.objects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by josh on 10/3/15.
 */
public class ReviewResult implements Parcelable {
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

	public ReviewResult(Parcel in) {
		Bundle data = in.readBundle();

		id = data.getString(ID);
		movie_id = data.getInt(MOVIE_ID);
		author = data.getString(AUTHOR);
		content = data.getString(CONTENT);
		url = data.getString(URL);
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int i) {
		Bundle args = new Bundle();
		args.putString(ID, id);
		args.putInt(MOVIE_ID, movie_id);
		args.putString(AUTHOR, author);
		args.putString(CONTENT, content);
		args.putString(URL, url);

		out.writeBundle(args);
	}
}

