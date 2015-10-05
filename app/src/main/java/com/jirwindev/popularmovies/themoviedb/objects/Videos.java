package com.jirwindev.popularmovies.themoviedb.objects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by josh on 9/27/15.
 */
public class Videos implements Parcelable {

	public static final String ID      = "id";
	public static final String RESULTS = "results";

	int           id;
	VideoResult[] results;

	public Videos() {
	}

	public Videos(Parcel in) {
		Bundle data = in.readBundle();
		id = data.getInt(ID);
		results = (VideoResult[]) data.getParcelableArray(RESULTS);
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int i) {
		Bundle args = new Bundle();
		args.putInt(ID, id);
		args.putParcelableArray(RESULTS, results);

		out.writeBundle(args);
	}
}
