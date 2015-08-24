package com.jirwindev.popularmovies;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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
        try {
            new PosterThread(posterImage).execute(new URL(args.getString(MoviePoster.POSTER_PATH)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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

    /**
     * Downloads the movie poster asynchronously
     */
    private class PosterThread extends AsyncTask<URL, Void, Bitmap> {

        private ImageView imageView;

        public PosterThread(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(URL... urls) {

            //Failsafe
            if (urls.length != 1)
                return null;
            URL url = urls[0];

            Bitmap poster = null;
            try {
                InputStream in = url.openStream();
                poster = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return poster;
        }

        @Override
        protected void onPostExecute(Bitmap poster) {
            super.onPostExecute(poster);

            imageView.setImageBitmap(poster);
        }
    }

}
