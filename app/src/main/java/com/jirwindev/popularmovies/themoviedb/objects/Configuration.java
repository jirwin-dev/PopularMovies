package com.jirwindev.popularmovies.themoviedb.objects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by josh on 9/16/15.
 */
public class Configuration implements Parcelable {

	//	public String images;
	public Images   images;
	public String[] change_keys;

	public static final String IMAGES      = "images";
	public static final String CHANGE_KEYS = "change_keys";

	public Configuration() {
	}

	public Configuration(Parcel in) {
		Bundle data = in.readBundle();
		images = data.getParcelable(IMAGES);
		change_keys = data.getStringArray(CHANGE_KEYS);
	}

	public Configuration(Images images, String[] change_keys) {
		this.images = images;
		this.change_keys = change_keys;
	}

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	public String[] getChange_keys() {
		return change_keys;
	}

	public void setChange_keys(String[] change_keys) {
		this.change_keys = change_keys;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int i) {
		Bundle args = new Bundle();
		args.putParcelable(IMAGES, images);
		args.putStringArray(CHANGE_KEYS, change_keys);

		out.writeBundle(args);
	}
}
