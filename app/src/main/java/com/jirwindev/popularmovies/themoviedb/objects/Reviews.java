package com.jirwindev.popularmovies.themoviedb.objects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by josh on 9/27/15.
 */
public class Reviews implements Parcelable {

	public static final String ID            = "id";
	public static final String PAGE          = "page";
	public static final String RESULTS       = "results";
	public static final String TOTAL_PAGES   = "total_pages";
	public static final String TOTAL_RESULTS = "total_results";

	int            id;
	int            page;
	ReviewResult[] results;
	int            total_pages;
	int            total_results;

	public Reviews() {
	}

	public Reviews(Parcel in) {
		Bundle data = in.readBundle();
		id = data.getInt(ID);
		page = data.getInt(PAGE);
		results = (ReviewResult[]) data.getParcelableArray(RESULTS);
		total_pages = data.getInt(TOTAL_PAGES);
		total_results = data.getInt(TOTAL_RESULTS);
	}

	public Reviews(int id, int page, ReviewResult[] results, int total_pages, int total_results) {
		this.id = id;
		this.page = page;
		this.results = results;
		this.total_pages = total_pages;
		this.total_results = total_results;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public ReviewResult[] getResults() {
		return results;
	}

	public void setResults(ReviewResult[] results) {
		this.results = results;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public void setTotal_pages(int total_pages) {
		this.total_pages = total_pages;
	}

	public int getTotal_results() {
		return total_results;
	}

	public void setTotal_results(int total_results) {
		this.total_results = total_results;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int i) {
		Bundle args = new Bundle();

		args.putInt(ID, id);
		args.putInt(PAGE, page);
		args.putParcelableArray(RESULTS, results);
		args.putInt(TOTAL_PAGES, total_pages);
		args.putInt(TOTAL_RESULTS, total_results);

		out.writeBundle(args);
	}
}
