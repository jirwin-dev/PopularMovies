package com.jirwindev.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jirwindev.popularmovies.themoviedb.objects.Movie;
import com.jirwindev.popularmovies.themoviedb.objects.Reviews;
import com.jirwindev.popularmovies.themoviedb.objects.Videos;

/**
 * Created by josh on 9/29/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 60;

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
				+ Movie.RUNTIME + " INTEGER)";
		db.execSQL(CREATE_MOVIES_TABLE);

		// Create Reviews Table
		String CREATE_REVIEWS_TABLE = "CREATE TABLE " + TABLE_REVIEWS + "("
				+ Reviews.Result.ID + " STRING PRIMARY KEY, "
				+ Movie.ID + " INTEGER, "
				+ Reviews.Result.AUTHOR + " TEXT, "
				+ Reviews.Result.CONTENT + " TEXT, "
				+ Reviews.Result.URL + " TEXT)";
		db.execSQL(CREATE_REVIEWS_TABLE);

		// Create Trailers Table
		String CREATE_VIDEOS_TABLE = "CREATE TABLE " + TABLE_VIDEOS + "("
				+ Videos.Result.ID + " STRING PRIMARY KEY, "
				+ Movie.ID + " INTEGER, "
				+ Videos.Result.KEY + " TEXT, "
				+ Videos.Result.NAME + " TEXT, "
				+ Videos.Result.SITE + " TEXT, "
				+ Videos.Result.SIZE + " INTEGER, "
				+ Videos.Result.TYPE + " TEXT)";
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
	 * TOWERS
	 */
	// Adding new Movie
	public void addOrUpdateMovie(Movie movie) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Movie.ID, movie.getId());
		values.put(Movie.TITLE, movie.getTitle());
		values.put(Movie.VOTE_AVERAGE, movie.getVoteAverage());
		values.put(Movie.OVERVIEW, movie.getOverview());
		values.put(Movie.RELEASE_DATE, movie.getReleaseDate());
		values.put(Movie.RUNTIME, movie.getRuntime());

		// Inserting Row
		db.insertWithOnConflict(TABLE_MOVIES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		db.close();
	}

	// Getting single Movie
	public Movie getTower(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_MOVIES, new String[]{
						Movie.ID,
						Movie.TITLE,
						Movie.VOTE_AVERAGE,
						Movie.OVERVIEW,
						Movie.RELEASE_DATE,
						Movie.RUNTIME
				},
				Movie.ID + "=?",
				new String[]{String.valueOf(id)},
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Movie movie = new Movie();
		movie.setId(cursor.getInt(0));
		movie.setTitle(cursor.getString(1));
		movie.setVoteAverage(cursor.getFloat(2));
		movie.setOverview(cursor.getString(3));
		movie.setReleaseDate(cursor.getString(4));
		movie.setRuntime(cursor.getInt(5));

		return movie;
	}

//	// Getting All Towers
//	public List<Tower> getTowersInRange(double gpsx, double gpsy, float radius) {
//		List<Tower> towerList = new ArrayList<Tower>();
//		// Select All Query
//		String selectQuery = "SELECT * FROM " + TABLE_TOWERS + " WHERE "
//				+ " (" + KEY_TOWER_GPSX + " <= " + (gpsx + radius) + " AND " + KEY_TOWER_GPSX + ">=" + (gpsx - radius) + ")"
//				+ " AND (" + KEY_TOWER_GPSY + " <= " + (gpsy + radius) + " AND " +
//				KEY_TOWER_GPSY + ">=" + (gpsy - radius) + ")";
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		for (int i = 0; i < cursor.getColumnCount(); i++)
//			Log.e("COLUMN NAME", cursor.getColumnName(i));
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				Tower tower = new Tower();
//				tower.setSiteId(cursor.getInt(0));
//				tower.setName(cursor.getString(1));
//				tower.setGpsx(cursor.getFloat(2));
//				tower.setGpsy(cursor.getFloat(3));
//				// Adding contact to list
//				towerList.add(tower);
//			}
//			while (cursor.moveToNext());
//		}
//
//		// return contact list
//		return towerList;
//	}
//
//	// Getting All Towers
//	public List<Tower> getAllTowers() {
//		List<Tower> towerList = new ArrayList<Tower>();
//		// Select All Query
//		String selectQuery = "SELECT * FROM " + TABLE_TOWERS
//				+ " ORDER BY " + KEY_TOWER_NAME + " ASC";
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		Tower select = new Tower(0, "Select a Tower");
//		towerList.add(select);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				Tower tower = new Tower();
//				tower.setSiteId(Integer.parseInt(cursor.getString(0)));
//				tower.setName(cursor.getString(1));
//				tower.setGpsx(cursor.getFloat(2));
//				tower.setGpsy(cursor.getFloat(3));
//				// Adding contact to list
//				towerList.add(tower);
//			}
//			while (cursor.moveToNext());
//		}
//
//		// return contact list
//		return towerList;
//	}
//

//

	/**
	 * Deletes a single movie
	 *
	 * @param tower
	 */
	public void deleteTower(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_MOVIES, Movie.ID + " = ?", new String[]{String.valueOf(id)});
		db.close();
	}


}