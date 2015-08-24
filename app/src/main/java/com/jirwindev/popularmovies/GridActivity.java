package com.jirwindev.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.jirwindev.popularmovies.threads.MovieDBThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class GridActivity extends Activity {

    //Elements
    private GridView gridMovies;
    private Spinner spinSortMethod;

    //Movie DB Data
    private ArrayList<MoviePoster> moviePosters;
    private JSONObject config;

    //Movie DB API
    final String MOVIEDB_CONFIG_URL = "http://api.themoviedb.org/3/configuration?api_key=";
    final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
    final String API_PARAM = "api_key";
    final String API_KEY = "REDACTED";
    final String SORT_PARAM = "sort_by";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        //Get Elements
        gridMovies = (GridView) findViewById(R.id.moviesGridView);
        spinSortMethod = (Spinner) findViewById(R.id.sortSpinner);

        //Assign Listeners
        spinSortMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                discoverMovies(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Set default values
        moviePosters = new ArrayList<MoviePoster>();
        getConfig();
    }

    /**
     * Creates the config uri and starts the call to the server
     */
    private void getConfig() {
        Uri configUri = Uri.parse(MOVIEDB_CONFIG_URL).buildUpon()
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();

        new ConfigThread(this).execute(configUri);
    }

    /**
     * Gets TheMovieDB config
     */
    private class ConfigThread extends MovieDBThread {

        public ConfigThread(Context context) {
            super(context);
            setupDialog(context, context.getString(R.string.config_thread_title));
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            config = jsonObject;
            discoverMovies(0);
        }
    }

    /**
     * Builds discovery uri and starts thread
     */
    private void discoverMovies(int sortIndex) {
        String sortMethod = getResources().getStringArray(R.array.sort_methods_api)[sortIndex];

        Uri discoverUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendQueryParameter(API_PARAM, API_KEY)
                .appendQueryParameter(SORT_PARAM, sortMethod)
                .build();

        new DiscoverThread(this).execute(discoverUri);
    }

    /**
     * Gets movie information from the movie db
     */
    private class DiscoverThread extends MovieDBThread {

        public DiscoverThread(Context context) {
            super(context);
            setupDialog(context, context.getString(R.string.discover_thread_title));
        }

        @Override
        protected void onPostExecute(JSONObject discover) {
            super.onPostExecute(discover);

            final String CONFIG_RESULTS = "results";
            final String CONFIG_IMAGES = "images";
            final String CONFIG_POSTER_SIZES = "poster_sizes";
            final String CONFIG_BASE_URL = "base_url";

            moviePosters = new ArrayList<MoviePoster>();
            try {
                JSONArray results = discover.getJSONArray(CONFIG_RESULTS);

                //Get config information
                JSONObject imageConfig = config.getJSONObject(CONFIG_IMAGES);
                JSONArray imageSizes = imageConfig.getJSONArray(CONFIG_POSTER_SIZES);
                String posterSize = imageSizes.getString((int) imageSizes.length() / 2); //get middle size
                String baseUrl = imageConfig.getString(CONFIG_BASE_URL);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject obj = results.getJSONObject(i);
                    MoviePoster movie = new MoviePoster();
                    movie.setId(obj.getInt(MoviePoster.ID));
                    movie.setPath(baseUrl + posterSize + obj.getString(MoviePoster.POSTER_PATH));
                    movie.setTitle(obj.getString(MoviePoster.TITLE));
                    movie.setPlotSynopsis(obj.getString(MoviePoster.OVERVIEW));
                    movie.setReleaseDate(obj.getString(MoviePoster.RELEASE_DATE));
                    movie.setPopularity(obj.getDouble(MoviePoster.POPULARITY));
                    movie.setVoteAverage(obj.getDouble(MoviePoster.VOTE_AVERAGE));
                    moviePosters.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Grid Adapter
            GridAdapter adapter = new GridAdapter(context);
            gridMovies.setAdapter(adapter);

            //Get visible images
            for (int i = 0; i < moviePosters.size(); i++) {
                if (moviePosters.get(i).getPoster() == null) {
                    new PosterThread(i).execute(moviePosters.get(i).getPath());
                    ImageView loadingImageView = new ImageView(context);
                    loadingImageView.setImageResource(android.R.drawable.progress_indeterminate_horizontal);
                }
            }
        }
    }

    /**
     * Adapter for gridview to display poster image views
     */
    public class GridAdapter extends BaseAdapter {

        private Context context;

        public GridAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return moviePosters.size();
        }

        @Override
        public Object getItem(int position) {
            Bitmap poster = moviePosters.get(position).getPoster();

            if (poster == null)
                new PosterThread(position).execute(moviePosters.get(position).getPath());
            return poster;
        }

        @Override
        public long getItemId(int position) {
            return moviePosters.get(position).getId();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(
                        GridLayout.LayoutParams.WRAP_CONTENT,
                        GridLayout.LayoutParams.WRAP_CONTENT
                ));
                imageView.setPadding(8, 8, 8, 8);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra(MoviePoster.TITLE, moviePosters.get(position).getTitle());
                        intent.putExtra(MoviePoster.POPULARITY, moviePosters.get(position).getPopularity());
                        intent.putExtra(MoviePoster.RELEASE_DATE, moviePosters.get(position).getReleaseDate());
                        intent.putExtra(MoviePoster.VOTE_AVERAGE, moviePosters.get(position).getVoteAverage());
                        intent.putExtra(MoviePoster.OVERVIEW, moviePosters.get(position).getPlotSynopsis());
                        intent.putExtra(MoviePoster.POSTER_PATH, moviePosters.get(position).getPath().toString());
                        startActivity(intent);
                    }
                });
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageBitmap(moviePosters.get(position).getPoster());
            return imageView;
        }
    }

    /**
     * Downloads the movie poster asynchronously
     */
    private class PosterThread extends AsyncTask<URL, Void, Bitmap> {

        private int position;

        public PosterThread(int position) {
            this.position = position;
        }

        @Override
        protected Bitmap doInBackground(URL... urls) {

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

            moviePosters.get(this.position).setPoster(poster);

            ImageView imageView = ((ImageView) gridMovies.getChildAt(position - gridMovies.getFirstVisiblePosition()));
            if (position <= gridMovies.getLastVisiblePosition() && imageView != null) {
                imageView.setImageBitmap(poster);
                imageView.invalidate();
            }
        }
    }
}
