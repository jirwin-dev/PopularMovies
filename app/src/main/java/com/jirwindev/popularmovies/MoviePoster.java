package com.jirwindev.popularmovies;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Wrapper class for movie data from  TheMovieDB
 */
public class MoviePoster implements Parcelable {

	private int    id;
	private URL    path;
	private Uri    poster;
	private String title;
	private String releaseDate;
	private double voteAverage;
	private double popularity;
	private String plotSynopsis;

	public static final String ID           = "id";
	public static final String POSTER_PATH  = "poster_path";
	public static final String TITLE        = "title";
	public static final String OVERVIEW     = "overview";
	public static final String RELEASE_DATE = "release_date";
	public static final String POPULARITY   = "popularity";
	public static final String VOTE_AVERAGE = "vote_average";

	public MoviePoster() {
	}

	public MoviePoster(int id) {
		this.id = id;
	}

	public MoviePoster(int id, String path, Uri poster) {
		this.id = id;
		this.poster = poster;
		try {
			this.path = new URL(path);
		}
		catch (MalformedURLException e) {
			this.path = null;
		}
	}

	public MoviePoster(Parcel in) {
		String[] data = new String[3];
		in.readStringArray(data);

		this.id = Integer.parseInt(data[0]);
		try {
			this.path = new URL(data[1]);
		}
		catch (MalformedURLException e) {
			this.poster = null;
			e.printStackTrace();
		}
		this.poster = Uri.parse(data[2]);
		this.title = data[3];
		this.releaseDate = data[4];
		this.voteAverage = Double.parseDouble(data[5]);
		this.popularity = Double.parseDouble(data[6]);
		this.plotSynopsis = data[7];
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Uri getPoster() {
		return poster;
	}

	public void setPoster(Uri poster) {
		this.poster = poster;
	}

	public URL getPath() {
		return path;
	}

	public void setPath(URL path) {
		this.path = path;
	}

	public void setPath(String path) {
		try {
			this.path = new URL(path);
		}
		catch (MalformedURLException e) {
			this.path = null;
		}
	}

	public String getPlotSynopsis() {
		return plotSynopsis;
	}

	public void setPlotSynopsis(String plotSynopsis) {
		this.plotSynopsis = plotSynopsis;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public double getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[]{
				id + "",
				path == null ? null : path.toString(),
				poster == null ? null : poster.toString(),
				title,
				releaseDate,
				voteAverage + "",
				popularity + "",
				plotSynopsis,
		});
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public MoviePoster createFromParcel(Parcel in) {
			return new MoviePoster(in);
		}

		public MoviePoster[] newArray(int size) {
			return new MoviePoster[size];
		}
	};
}