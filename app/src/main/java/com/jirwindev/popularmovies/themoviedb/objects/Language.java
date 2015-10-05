package com.jirwindev.popularmovies.themoviedb.objects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by josh on 10/4/15.
 */
public class Language implements Parcelable {
	String iso_639_1;
	String name;

	public static final String ISO  = "iso";
	public static final String NAME = "name";

	public Language() {

	}

	public Language(Parcel in) {

		Bundle data = in.readBundle();
		this.iso_639_1 = data.getString(ISO);
		this.name = data.getString(NAME);
	}

	public Language(String iso_639_1, String name) {
		this.iso_639_1 = iso_639_1;
		this.name = name;
	}

	public String getIso_639_1() {
		return iso_639_1;
	}

	public void setIso_639_1(String iso_639_1) {
		this.iso_639_1 = iso_639_1;
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
		args.putString(ISO, iso_639_1);
		args.putString(NAME, name);
		out.writeBundle(args);
	}
}
