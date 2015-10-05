package com.jirwindev.popularmovies.themoviedb.objects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by josh on 10/4/15.
 */
public class Collection implements Parcelable {

	int    id;
	String name, poster_path, backdrop_path;

	public static final String ID            = "id";
	public static final String NAME          = "name";
	public static final String POSTER_PATH   = "poster_path";
	public static final String BACKDROP_PATH = "backdrop_path";

	public Collection() {
	}

	public Collection(Parcel in) {
		Bundle data = in.readBundle();
		id = data.getInt(ID);
		name = data.getString(NAME);
		poster_path = data.getString(POSTER_PATH);
		backdrop_path = data.getString(BACKDROP_PATH);
	}

	public Collection(int id, String name, String poster_path, String backdrop_path) {
		this.id = id;
		this.name = name;
		this.poster_path = poster_path;
		this.backdrop_path = backdrop_path;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int i) {
		Bundle args = new Bundle();
		args.putInt(ID, id);
		args.putString(NAME, name);
		args.putString(POSTER_PATH, poster_path);
		args.putString(BACKDROP_PATH, backdrop_path);
	}
}
