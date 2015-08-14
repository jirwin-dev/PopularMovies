package com.jirwindev.popularmovies;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class GridActivityFragment extends Fragment {

    private GridView gridMovies;
    private ArrayList<MoviePoster> moviePosters;

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
        getMovieIds();

        return v;
    }

    private void getMovieIds() {
        new GetMoviePosterIdsThread(getActivity()).execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class MoviePoster {

        private int id;
        private Drawable poster;

        public MoviePoster() {
        }

        public MoviePoster(int id) {
            this.id = id;
        }

        public MoviePoster(int id, Drawable poster) {
            this.id = id;
            this.poster = poster;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Drawable getPoster() {
            return poster;
        }

        public void setPoster(Drawable poster) {
            this.poster = poster;
        }
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
            if (moviePosters.get(position).getPoster() == null) {
                new GetMoviePosterThread(position).execute(moviePosters.get(position).getId());
                ImageView loadingImageView = new ImageView(context);
                loadingImageView.setImageResource(android.R.drawable.progress_indeterminate_horizontal);
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
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageDrawable(moviePosters.get(position).getPoster());
            return imageView;
        }
    }

    private class GetMoviePosterThread extends AsyncTask<Integer, Integer, MoviePoster> {

        private int position;

        public GetMoviePosterThread(int position) {
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MoviePoster doInBackground(Integer... params) {
            return null;
        }

        @Override
        protected void onPostExecute(MoviePoster poster) {
            super.onPostExecute(poster);

            moviePosters.set(this.position, poster);
        }

    }

    private class GetMoviePosterIdsThread extends AsyncTask<Void, Void, ArrayList<MoviePoster>> {

        private ProgressDialog dialog;

        public GetMoviePosterIdsThread(Context context) {
            dialog = new ProgressDialog(context);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected ArrayList<MoviePoster> doInBackground(Void... params) {
            ArrayList<MoviePoster> m = new ArrayList<MoviePoster>();

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                final String MOVIEDB_BASE_URL =
                        "http://api.themoviedb.org/3/discover/movie?";
                final String API_PARAM = "api_key";
                final String API_KEY = "REDACTED";

                Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                        .appendQueryParameter(API_PARAM, API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(this.getClass().getSimpleName(), "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();

                Log.e("RETURN", new JSONObject(forecastJsonStr).toString(4));
            } catch (MalformedURLException e) {
                e.printStackTrace(); //TODO Handle
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return m;
        }

        @Override
        protected void onPostExecute(ArrayList<MoviePoster> moviePosters) {
            super.onPostExecute(moviePosters);

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }
}
