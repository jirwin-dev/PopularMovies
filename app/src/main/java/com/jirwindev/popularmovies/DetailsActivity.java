package com.jirwindev.popularmovies;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		//Setup ActionBar
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		//Get Movie Data
		Bundle args = getIntent().getExtras();

		//Get Elements
		ImageView posterImage = (ImageView) findViewById(R.id.posterImageView);
		TextView title = (TextView) findViewById(R.id.titleTextView);
		TextView releaseDate = (TextView) findViewById(R.id.releaseDateTextView);
		TextView voteAverage = (TextView) findViewById(R.id.voteAverageTextView);
		TextView overview = (TextView) findViewById(R.id.overviewTextView);

		//Set defaults
		posterImage.setImageURI(Uri.parse(args.getString(MoviePoster.POSTER_PATH)));
		title.setText(args.getString(MoviePoster.TITLE));
		releaseDate.setText(args.getString(MoviePoster.RELEASE_DATE));
		voteAverage.setText(args.getDouble(MoviePoster.VOTE_AVERAGE) + "");
		overview.setText(args.getString(MoviePoster.OVERVIEW));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		this.finish();
	}

}
