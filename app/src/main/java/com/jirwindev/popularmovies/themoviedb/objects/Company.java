package com.jirwindev.popularmovies.themoviedb.objects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by josh on 10/4/15.
 */
public class Company implements Parcelable {

	int    id;
	String name;

	public static final String ID   = "id";
	public static final String NAME = "name";

	public Company() {
	}

	public Company(Parcel in) {
		Bundle data = in.readBundle();

		id = data.getInt(ID);
		name = data.getString(NAME);
	}

	public Company(int id, String name) {
		this.id = id;
		this.name = name;
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

	public void setName(String name) {
		this.name = name;
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

		out.writeBundle(args);
	}
}
