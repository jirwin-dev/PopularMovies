package com.jirwindev.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jirwindev.popularmovies.themoviedb.objects.Movie;
import com.jirwindev.popularmovies.themoviedb.objects.ReviewResult;
import com.jirwindev.popularmovies.themoviedb.objects.Reviews;
import com.jirwindev.popularmovies.themoviedb.objects.VideoResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josh on 9/29/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 65;

	// Database Name
	private static final String DATABASE_NAME = "popular_movies";

	// Table names
	private static final String TABLE_MOVIES  = "movies";
	private static final String TABLE_REVIEWS = "reviews";
	private static final String TABLE_VIDEOS  = "videos";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create Towers Table
		String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
				+ Movie.ID + " INTEGER PRIMARY KEY, "
				+ Movie.TITLE + " TEXT, "
				+ Movie.VOTE_AVERAGE + " REAL, "
				+ Movie.OVERVIEW + " TEXT, "
				+ Movie.RELEASE_DATE + " TEXT, "
				+ Movie.RUNTIME + " INTEGER, "
				+ Movie.POSTER_PATH + " TEXT)";
		db.execSQL(CREATE_MOVIES_TABLE);

		// Create Reviews Table
		String CREATE_REVIEWS_TABLE = "CREATE TABLE " + TABLE_REVIEWS + "("
				+ ReviewResult.ID + " STRING PRIMARY KEY, "
				+ ReviewResult.MOVIE_ID + " INTEGER, "
				+ ReviewResult.AUTHOR + " TEXT, "
				+ ReviewResult.CONTENT + " TEXT, "
				+ ReviewResult.URL + " TEXT)";
		db.execSQL(CREATE_REVIEWS_TABLE);

		// Create Trailers Table
		String CREATE_VIDEOS_TABLE = "CREATE TABLE " + TABLE_VIDEOS + "("
				+ VideoResult.ID + " STRING PRIMARY KEY, "
				+ VideoResult.MOVIE_ID + " INTEGER, "
				+ VideoResult.KEY + " TEXT, "
				+ VideoResult.NAME + " TEXT, "
				+ VideoResult.SITE + " TEXT, "
				+ VideoResult.SIZE + " INTEGER, "
				+ VideoResult.TYPE + " TEXT)";
		db.execSQL(CREATE_VIDEOS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEOS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Tries to add a movie to the database. If the movie already exists, then it updates the
	 * existing movie.
	 *
	 * @param movie
	 */
	public void addOrUpdateMovie(Movie movie) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Movie.ID, movie.getId());
		values.put(Movie.TITLE, movie.getTitle());
		values.put(Movie.VOTE_AVERAGE, movie.getVoteAverage());
		values.put(Movie.OVERVIEW, movie.getOverview());
		values.put(Movie.RELEASE_DATE, movie.getReleaseDate());
		values.put(Movie.RUNTIME, movie.getRuntime());
		values.put(Movie.POSTER_PATH, movie.getPosterPath());

		// Inserting Row
		db.insertWithOnConflict(TABLE_MOVIES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		db.close();
	}

	/**
	 * Gets a single Movie based on its id
	 *
	 * @param id
	 *
	 * @return Movie movie
	 */
	public Movie getMovie(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_MOVIES, new String[]{
						Movie.ID,
						Movie.TITLE,
						Movie.VOTE_AVERAGE,
						Movie.OVERVIEW,
						Movie.RELEASE_DATE,
						Movie.RUNTIME,
						Movie.POSTER_PATH
				},
				Movie.ID + "=?",
				new String[]{String.valueOf(id)},
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		if (cursor.getCount() == 0)
			return null;

		Log.e("COUNT", cursor.getCount() + "");
		Log.e("STRING 0", cursor.getString(0));

		Movie movie = new Movie();
		movie.setId(cursor.getInt(0));
		movie.setTitle(cursor.getString(1));
		movie.setVoteAverage(cursor.getFloat(2));
		movie.setOverview(cursor.getString(3));
		movie.setReleaseDate(cursor.getString(4));
		movie.setRuntime(cursor.getInt(5));
		movie.setPosterPath(cursor.getString(6));

		db.close();

		return movie;
	}

	/**
	 * Returns all movies
	 *
	 * @return List<Movie> movies
	 */
	public List<Movie> getAllMovies() {
		List<Movie> movieList = new ArrayList<Movie>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_MOVIES;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Movie movie = new Movie();
				movie.setId(cursor.getInt(0));
				movie.setTitle(cursor.getString(1));
				movie.setVoteAverage(cursor.getFloat(2));
				movie.setOverview(cursor.getString(3));
				movie.setReleaseDate(cursor.getString(4));
				movie.setRuntime(cursor.getInt(5));
				movie.setPosterPath(cursor.getString(6));
				movieList.add(movie);
			}
			while (cursor.moveToNext());
		}

		db.close();

		return movieList;
	}

	/**
	 * Deletes a single movie
	 *
	 * @param int id
	 */
	public void deleteMovie(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_MOVIES, Movie.ID + " = ?", new String[]{String.valueOf(id)});
		db.close();
	}

	/**
	 * Tries to add a Review to the database, and updates it if it already exists
	 *
	 * @param ReviewResult review
	 */
	public void addOrUpdateReviews(Reviews reviews) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.beginTransaction();
		for (ReviewResult review : reviews.getResults()) {
			ContentValues values = new ContentValues();
			values.put(ReviewResult.ID, review.getId());
			values.put(ReviewResult.MOVIE_ID, reviews.getId());
			values.put(ReviewResult.AUTHOR, review.getAuthor());
			values.put(ReviewResult.CONTENT, review.getContent());
			values.put(ReviewResult.URL, review.getUrl());

			db.insertWithOnConflict(TABLE_REVIEWS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}

	/**
	 * Gets a single review
	 *
	 * @param id
	 *
	 * @return ReviewResult review
	 */
	public ReviewResult getReview(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_REVIEWS, new String[]{
						ReviewResult.ID,
						ReviewResult.MOVIE_ID,
						ReviewResult.AUTHOR,
						ReviewResult.CONTENT,
						ReviewResult.URL
				},
				ReviewResult.ID + "=?",
				new String[]{String.valueOf(id)},
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		if (cursor.getCount() == 0)
			return null;

		ReviewResult review = new ReviewResult();
		review.setId(cursor.getString(0));
		review.setMovieId(cursor.getInt(1));
		review.setAuthor(cursor.getString(2));
		review.setContent(cursor.getString(3));
		review.setUrl(cursor.getString(4));

		db.close();

		return review;
	}

	/**
	 * Returns a list of movie reviews for a certain movie id
	 *
	 * @param id
	 *
	 * @return List<ReviewResult> reviewList
	 */
	public List<ReviewResult> getReviewsByMovie(int id) {
		List<ReviewResult> reviewList = new ArrayList<ReviewResult>();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_REVIEWS, new String[]{
						ReviewResult.ID,
						ReviewResult.MOVIE_ID,
						ReviewResult.AUTHOR,
						ReviewResult.CONTENT,
						ReviewResult.URL
				},
				ReviewResult.MOVIE_ID + "=?",
				new String[]{String.valueOf(id)},
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ReviewResult review = new ReviewResult();
				review.setId(cursor.getString(0));
				review.setMovieId(cursor.getInt(1));
				review.setAuthor(cursor.getString(2));
				review.setContent(cursor.getString(3));
				review.setUrl(cursor.getString(4));
				reviewList.add(review);
			}
			while (cursor.moveToNext());
		}

		db.close();

		return reviewList;
	}

	public void deleteReviewsByMovie(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_REVIEWS, ReviewResult.MOVIE_ID + " = ?", new String[]{String.valueOf(id)});
		db.close();
	}
}