package com.jirwindev.popularmovies;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.jirwindev.popularmovies.threads.GetMoviePosterIdsThread;
import com.jirwindev.popularmovies.threads.MovieDBThread;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class GridActivityFragment extends Fragment {

    private GridView gridMovies;
    private ArrayList<MoviePoster> moviePosters;
    private JSONObject config;

    final String MOVIEDB_CONFIG_URL = "http://api.themoviedb.org/3/configuration?api_key=";
    final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
    final String API_PARAM = "api_key";
    final String API_KEY = "16a2f4ed9f33bbef261ee48e3903443f";

    public GridActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grid, container, false);

        //Get Elements
        gridMovies = (GridView) v.findViewById(R.id.moviesGridView);

        //Assign Listeners

        //Set default values
        moviePosters = new ArrayList<MoviePoster>();
        getConfig();

        return v;
    }

    private void getConfig() {
        Uri configUri = Uri.parse(MOVIEDB_CONFIG_URL).buildUpon()
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();

        new GetMovieDBConfigThread().execute(configUri);
    }

    /**
     * Gets TheMovieDB config
     */
    private class GetMovieDBConfigThread extends MovieDBThread {

        public GetMovieDBConfigThread(Context context, String title) {
            super();
            setupDialog(context, title);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            if (jsonObject == null) {
                //display error connecting
            } else
                config = jsonObject;
        }
    }

    private void getMovieIds() {
        new GetMoviePosterIdsThread(getActivity()).execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private class GridAdapter extends BaseAdapter {

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
            Log.e("ADAPTER", "GET ITEM CALLED");
            if (moviePosters.get(position).getPoster() == null) {
                new GetMoviePosterThread(position).execute(moviePosters.get(position).getPath());
            }
            return moviePosters.get(position).getPoster();
        }

        @Override
        public long getItemId(int position) {
            return moviePosters.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(
                        GridLayout.LayoutParams.WRAP_CONTENT,
                        GridLayout.LayoutParams.WRAP_CONTENT
                ));
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(8, 8, 8, 8);
            } else if (convertView instanceof ProgressBar) {
                return (ProgressBar) convertView;
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageBitmap(moviePosters.get(position).getPoster());
            return imageView;
        }
    }

    private class GetMoviePosterThread extends AsyncTask<String, Void, Bitmap> {

        private int position;

        public GetMoviePosterThread(int position) {
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e("GETTING IMAGE AT", params[0]);
            String urldisplay = params[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap poster) {
            super.onPostExecute(poster);

            moviePosters.get(this.position).setPoster(poster);

            if (position <= gridMovies.getLastVisiblePosition()) {
                ((ImageView) gridMovies.getChildAt(position - gridMovies.getFirstVisiblePosition())).setImageBitmap(poster);
                ((ImageView) gridMovies.getChildAt(position - gridMovies.getFirstVisiblePosition())).invalidate();
            }
        }

    }
}
