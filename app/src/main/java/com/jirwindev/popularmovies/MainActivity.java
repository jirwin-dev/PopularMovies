package com.jirwindev.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jirwindev.popularmovies.themoviedb.objects.Movie;

/**
 * Created by josh on 10/5/15.
 */
public class MainActivity extends Activity implements Communicator {

	private static final String TAG_DETAIL = "detail";
	private static final String TAG_GRID   = "grid";

	private static final String STATE_SORT_POPULARITY = "popularity";
	private static final String STATE_SORT_RATING     = "rating";
	private static final String STATE_SUBTITLE        = "subtitle";

	private boolean twoPane;
	private Menu    menu;
	private boolean sortPopularity, sortRating;

	@Override
	protected void onCreate(Bundle inState) {
		super.onCreate(inState);

		setContentView(R.layout.activity_main);

		//Setup Fragments
		if (findViewById(R.id.detailsContainer) != null) {
			twoPane = true;
		}
		else {
			twoPane = false;
		}
		Log.wtf("TWO PANE", twoPane + "");

		//Setup sort
		try {
			sortPopularity = inState.getBoolean(STATE_SORT_POPULARITY);
		}
		catch (Exception e) {
			sortPopularity = true;
		}
		try {
			sortRating = inState.getBoolean(STATE_SORT_RATING);
		}
		catch (Exception e) {
			sortRating = false;
		}

		//Restore title
		if (inState != null && inState.getString(STATE_SUBTITLE) != null)
			getActionBar().setSubtitle(inState.getString(STATE_SUBTITLE));
		else
			getActionBar().setSubtitle(getString(R.string.poplarity_title));

		//Add Grid fragment if not tablet
		if (!twoPane)
			getFragmentManager().beginTransaction()
					.replace(R.id.fragment_grid, new GridFragment(), TAG_GRID)
					.commit();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(STATE_SORT_RATING, menu.findItem(R.id.sort_rating).isChecked());
		outState.putBoolean(STATE_SORT_POPULARITY, menu.findItem(R.id.sort_popular).isChecked());
		outState.putString(STATE_SUBTITLE, getActionBar().getSubtitle().toString());
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.grid_menu, menu);
		this.menu = menu;
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.sort_popular).setChecked(sortPopularity);
		menu.findItem(R.id.sort_rating).setChecked(sortRating);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		//Get grid fragment
		GridFragment gridFragment = (GridFragment) getFragmentManager()
				.findFragmentById(R.id.fragment_grid);

		switch (item.getItemId()) {
			case R.id.favorites:
				menu.findItem(R.id.sort_rating).setChecked(false);
				menu.findItem(R.id.sort_popular).setChecked(false);
				getActionBar().setSubtitle(getString(R.string.favorites_title));
				gridFragment.getFavorites();
				return true;
			case R.id.sort_popular:
				menu.findItem(R.id.sort_rating).setChecked(item.isChecked());
				item.setChecked(!item.isChecked());
				gridFragment.getMovies(getString(R.string.sort_popular_param));
				getActionBar().setSubtitle(getString(R.string.poplarity_title));
				return true;
			case R.id.sort_rating:
				menu.findItem(R.id.sort_popular).setChecked(item.isChecked());
				item.setChecked(!item.isChecked());
				gridFragment.getMovies(getString(R.string.sort_rating_param));
				getActionBar().setSubtitle(getString(R.string.rating_title));
				return true;
		}
		return false;
	}

	@Override
	public void showDetails(Movie movie) {
		DetailsFragment detailsFrag = new DetailsFragment();
		Bundle args = new Bundle();
		args.putInt(Movie.ID, movie.getId());
		args.putString(Movie.TITLE, movie.getTitle());
		args.putDouble(Movie.POPULARITY, movie.getPopularity());
		args.putString(Movie.RELEASE_DATE, movie.getReleaseDate());
		args.putDouble(Movie.VOTE_AVERAGE, movie.getVoteAverage());
		args.putString(Movie.OVERVIEW, movie.getOverview());
		if (movie.getPoster() != null)
			args.putString(Movie.POSTER_PATH, movie.getPoster().toString());
		detailsFrag.setArguments(args);

		if (twoPane) {
			getFragmentManager().beginTransaction()
					.replace(R.id.detailsContainer, detailsFrag, TAG_DETAIL)
					.commit();
		}
		else {
			getFragmentManager().beginTransaction()
					.replace(R.id.fragment_grid, detailsFrag, TAG_DETAIL)
					.addToBackStack(TAG_DETAIL)
					.commit();
		}
	}

	@Override
	public void onBackPressed() {
		if (!twoPane && getFragmentManager().getBackStackEntryCount() > 0)
			getFragmentManager().popBackStack();
		else
			super.onBackPressed();
	}

	@Override
	public String getSort() {
		MenuItem sortPopular = menu.findItem(R.id.sort_popular);
		MenuItem sortRating = menu.findItem(R.id.sort_rating);

		if (sortPopular.isChecked())
			return getString(R.string.sort_popular_param);
		else if (sortRating.isChecked())
			return getString(R.string.sort_rating_param);

		return null;
	}
}
