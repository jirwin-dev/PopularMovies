package com.jirwindev.popularmovies.database;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by josh on 9/29/15.
 */
public class DatabaseHandler {

	public DatabaseHandler() {
		String tableName = getClass().getSimpleName();
		Log.i("Table Name", getClass().getSimpleName());

		ArrayList<String> rows = new ArrayList<String>();
		ArrayList<String> types = new ArrayList<String>();

		for (Field field : getClass().getFields()) {
			rows.add(field.getName());
			types.add(field.getType().toString());

			Log.i("Row", field.getName());
			Log.i("Type", field.getType().getSimpleName());
		}
	}
}
