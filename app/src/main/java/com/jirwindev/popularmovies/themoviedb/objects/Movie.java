package com.jirwindev.popularmovies.themoviedb.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by josh on 9/28/15.
 */
public class Movie implements Parcelable {


	boolean    adult;
	String     backdrop_path;
	Collection belongs_to_collection;
	long       budget;
	Genre[]    genres;
	String     homepage;
	int        id;
	String     imdb_id;
	String     original_language;
	String     original_title;
	String     overview;
	double     popularity;
	String     poster_path;
	Company[]  production_companies;
	Country[]  production_countries;
	String     release_date;
	long       revenue;
	int        runtime;
	Language[] spoken_languages;
	String     status;
	String     tagline;
	String     title;
	boolean    video;
	double     vote_average;
	int        vote_count;
	Uri        poster;

	public static final String ID                    = "id";
	public static final String TITLE                 = "title";
	public static final String VOTE_AVERAGE          = "vote_average";
	public static final String OVERVIEW              = "overview";
	public static final String RELEASE_DATE          = "release_date";
	public static final String RUNTIME               = "runtime";
	public static final String POSTER_PATH           = "poster_path";
	public static final String ADULT                 = "adult";
	public static final String BACKDROP_PATH         = "backdrop_path";
	public static final String BELONGS_TO_COLLECTION = "belongs_to_collection";
	public static final String BUDGET                = "budget";
	public static final String GENRES                = "genres";
	public static final String HOMEPAGE              = "homepage";
	public static final String IMDB_ID               = "imdb_id";
	public static final String ORIGINAL_LANGUAGE     = "original_language";
	public static final String ORIGINAL_TITLE        = "original_title";
	public static final String POPULARITY            = "popularity";
	public static final String PRODUCTION_COMPANIES  = "production_companies";
	public static final String PRODUCTION_COUNTRIES  = "production_countries";
	public static final String REVENUE               = "revenue";
	public static final String SPOKEN_LANGUAGES      = "spoken_languages";
	public static final String STATUS                = "status";
	public static final String TAGLINE               = "tagline";
	public static final String VIDEO                 = "video";
	public static final String VOTE_COUNT            = "vote_count";
	public static final String POSTER                = "poster";


	public Movie() {

	}

	public Movie(boolean adult, String backdrop_path, Genre[] genres, int id,
				 String original_language, String original_title, String overview, String release_date, String poster_path, double popularity, String title, boolean video, double vote_average, int vote_count) {
		this.adult = adult;
		this.backdrop_path = backdrop_path;
		this.genres = genres;
		this.id = id;
		this.original_language = original_language;
		this.original_title = original_title;
		this.overview = overview;
		this.release_date = release_date;
		this.poster_path = poster_path;
		this.popularity = popularity;
		this.title = title;
		this.video = video;
		this.vote_average = vote_average;
		this.vote_count = vote_count;
	}

	public Movie(boolean adult, String backdrop_path, Collection belongs_to_collection,
				 long budget, Genre[] genres, String homepage, int id, String imdb_id,
				 String original_language, String original_title, String overview,
				 double popularity, String poster_path, Company[] production_companies,
				 Country[] production_countries, String release_date, long revenue, int runtime,
				 Language[] spoken_languages, String status, String tagline, String title,
				 boolean video, double vote_average, int vote_count) {
		this.adult = adult;
		this.backdrop_path = backdrop_path;
		this.belongs_to_collection = belongs_to_collection;
		this.budget = budget;
		this.genres = genres;
		this.homepage = homepage;
		this.id = id;
		this.imdb_id = imdb_id;
		this.original_language = original_language;
		this.original_title = original_title;
		this.overview = overview;
		this.popularity = popularity;
		this.poster_path = poster_path;
		this.production_companies = production_companies;
		this.production_countries = production_countries;
		this.release_date = release_date;
		this.revenue = revenue;
		this.runtime = runtime;
		this.spoken_languages = spoken_languages;
		this.status = status;
		this.tagline = tagline;
		this.title = title;
		this.video = video;
		this.vote_average = vote_average;
		this.vote_count = vote_count;
	}

	public Movie(Parcel in) {
		Bundle data = in.readBundle();

		this.adult = data.getBoolean(ADULT);
		this.backdrop_path = data.getString(BACKDROP_PATH);
		this.belongs_to_collection = data.getParcelable(BELONGS_TO_COLLECTION);
		this.budget = data.getLong(BUDGET);
		this.genres = (Genre[]) data.getParcelableArray(GENRES);
		this.homepage = data.getString(HOMEPAGE);
		this.id = data.getInt(ID);
		this.imdb_id = data.getString(IMDB_ID);
		this.original_language = data.getString(ORIGINAL_LANGUAGE);
		this.original_title = data.getString(ORIGINAL_TITLE);
		this.overview = data.getString(OVERVIEW);
		this.popularity = data.getDouble(POPULARITY);
		this.poster_path = data.getString(POSTER_PATH);
		this.production_companies = (Company[]) data.getParcelableArray(PRODUCTION_COMPANIES);
		this.production_countries = (Country[]) data.getParcelableArray(PRODUCTION_COUNTRIES);
		this.release_date = data.getString(RELEASE_DATE);
		this.revenue = data.getLong(REVENUE);
		this.runtime = data.getInt(RUNTIME);
		this.spoken_languages = (Language[]) data.getParcelableArray(SPOKEN_LANGUAGES);
		this.status = data.getString(STATUS);
		this.tagline = data.getString(TAGLINE);
		this.title = data.getString(TITLE);
		this.video = data.getBoolean(VIDEO);
		this.vote_average = data.getDouble(VOTE_AVERAGE);
		this.vote_count = data.getInt(VOTE_COUNT);
		this.poster = Uri.parse(data.getString(POSTER));
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int i) {
		Bundle args = new Bundle();
		args.putBoolean(ADULT, adult);
		args.putString(BACKDROP_PATH, backdrop_path);
		args.putParcelable(BELONGS_TO_COLLECTION, belongs_to_collection);
		args.putLong(BUDGET, budget);
		args.putParcelableArray(GENRES, genres);
		args.putString(HOMEPAGE, homepage);
		args.putInt(ID, id);
		args.putString(IMDB_ID, imdb_id);
		args.putString(ORIGINAL_LANGUAGE, original_language);
		args.putString(ORIGINAL_TITLE, original_title);
		args.putString(OVERVIEW, overview);
		args.putDouble(POPULARITY, popularity);
		args.putString(POSTER_PATH, poster_path);
		args.putParcelableArray(PRODUCTION_COMPANIES, production_companies);
		args.putParcelableArray(PRODUCTION_COUNTRIES, production_countries);
		args.putString(RELEASE_DATE, release_date);
		args.putLong(REVENUE, revenue);
		args.putInt(RUNTIME, runtime);
		args.putParcelableArray(SPOKEN_LANGUAGES, spoken_languages);
		args.putString(STATUS, status);
		args.putString(TAGLINE, tagline);
		args.putString(TITLE, title);
		args.putBoolean(VIDEO, video);
		args.putDouble(VOTE_AVERAGE, vote_average);
		args.putInt(VOTE_COUNT, vote_count);
		args.putString(POSTER, poster.toString());
		out.writeBundle(args);
	}

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public String getBackdrop_path() {
		return backdrop_path;
	}

	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}

	public Collection getBelongs_to_collection() {
		return belongs_to_collection;
	}

	public void setBelongs_to_collection(Collection belongs_to_collection) {
		this.belongs_to_collection = belongs_to_collection;
	}

	public long getBudget() {
		return budget;
	}

	public void setBudget(long budget) {
		this.budget = budget;
	}

	public Genre[] getGenres() {
		return genres;
	}

	public void setGenres(Genre[] genres) {
		this.genres = genres;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImdb_id() {
		return imdb_id;
	}

	public void setImdb_id(String imdb_id) {
		this.imdb_id = imdb_id;
	}

	public String getOriginal_language() {
		return original_language;
	}

	public void setOriginal_language(String original_language) {
		this.original_language = original_language;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public String getPosterPath() {
		return poster_path;
	}

	public void setPosterPath(String poster_path) {
		this.poster_path = poster_path;
	}

	public Company[] getProduction_companies() {
		return production_companies;
	}

	public void setProduction_companies(Company[] production_companies) {
		this.production_companies = production_companies;
	}

	public Country[] getProduction_countries() {
		return production_countries;
	}

	public void setProduction_countries(Country[] production_countries) {
		this.production_countries = production_countries;
	}

	public String getReleaseDate() {
		return release_date;
	}

	public void setReleaseDate(String release_date) {
		this.release_date = release_date;
	}

	public long getRevenue() {
		return revenue;
	}

	public void setRevenue(long revenue) {
		this.revenue = revenue;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public Language[] getSpoken_languages() {
		return spoken_languages;
	}

	public void setSpoken_languages(Language[] spoken_languages) {
		this.spoken_languages = spoken_languages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public double getVoteAverage() {
		return vote_average;
	}

	public void setVoteAverage(double vote_average) {
		this.vote_average = vote_average;
	}

	public int getVote_count() {
		return vote_count;
	}

	public void setVote_count(int vote_count) {
		this.vote_count = vote_count;
	}

	public Uri getPoster() {
		return poster;
	}

	public void setPoster(Uri poster) {
		this.poster = poster;
	}

	class PosterDownloadThread extends AsyncTask<URL, Void, Uri> {

		private ImageView imageView;

		public PosterDownloadThread(ImageView imageView) {
			this.imageView = imageView;
		}

		@Override
		protected Uri doInBackground(URL... urls) {
			Log.e(getClass().getSimpleName(), "running...");

			if (urls.length != 1)
				return null;
			URL url = urls[0];

			//Get Storage Directory
			String root = Environment.getExternalStorageDirectory().toString();
			File storageDir = new File(root + "/posters");
			Log.wtf(getClass().getSimpleName(), "Creating storage dirs...");
			boolean makeDirs = storageDir.mkdirs();
			if (makeDirs)
				Log.wtf(getClass().getSimpleName(), "Created storages dirs...");
			else
				Log.wtf(getClass().getSimpleName(), "Couldn't create storages dirs...");

			//Save poster to file
			String fname = url.getFile();
			File file = new File(storageDir, fname);

			if (!file.exists()) {
				try {
					//Download bitmap
					Bitmap poster = null;
					InputStream in = url.openStream();
					poster = BitmapFactory.decodeStream(in);

					//Save to file
					FileOutputStream out = new FileOutputStream(file);
					poster.compress(Bitmap.CompressFormat.JPEG, 90, out);
					out.flush();
					out.close();
				}
				catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}

			return Uri.fromFile(file);
		}

		@Override
		protected void onPostExecute(Uri p) {
			super.onPostExecute(p);

			poster = p;

//				ImageView imageView = ((ImageView) gridMovies.getChildAt(position - gridMovies.getFirstVisiblePosition()));
//				if (position <= gridMovies.getLastVisiblePosition() && imageView != null) {
			Log.e("IMAGE", "SET AND INVALIDATED");
			imageView.setImageURI(p);
			imageView.invalidate();
//				}
		}
	}
}
