package com.jirwindev.popularmovies;

import android.app.Fragment;
import android.app.ProgressDialog;
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

import org.json.JSONArray;
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
    private JSONObject config;

    final String MOVIEDB_CONFIG_URL = "http://api.themoviedb.org/3/configuration?api_key=";
    final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
    final String API_PARAM = "api_key";
    final String API_KEY = "REDACTED";

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
        private String path;
        private Bitmap poster;

        public MoviePoster() {
        }

        public MoviePoster(int id) {
            this.id = id;
        }

        public MoviePoster(int id, String path, Bitmap poster) {
            this.id = id;
            this.path = path;
            this.poster = poster;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Bitmap getPoster() {
            return poster;
        }

        public void setPoster(Bitmap poster) {
            this.poster = poster;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
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

            if (moviePosters.get(position).getPoster() == null) {
                ProgressBar progressBar = new ProgressBar(getActivity());
                progressBar.setIndeterminate(true);
                return progressBar;
            } else
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
                //TODO CHECK FOR PROGRESS BAR
                ((ImageView) gridMovies.getChildAt(position - gridMovies.getFirstVisiblePosition())).setImageBitmap(poster);
                ((ImageView) gridMovies.getChildAt(position - gridMovies.getFirstVisiblePosition())).invalidate();
            }
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
                config = getConfig();

                JSONObject movies = getMovies();
                JSONArray results = movies.getJSONArray("results");
                JSONObject imageConfig = config.getJSONObject("images");
                JSONArray imageSizes = imageConfig.getJSONArray("poster_sizes");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject obj = results.getJSONObject(i);
                    MoviePoster movie = new MoviePoster();
                    movie.setId(obj.getInt("id"));
                    movie.setPath(imageConfig.getString("base_url")
                            + imageSizes.getString((int) imageSizes.length() / 2)
                            + obj.getString("poster_path"));
//                    movie.setPoster();
                    m.add(movie);
                }
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
        protected void onPostExecute(ArrayList<MoviePoster> p) {
            super.onPostExecute(p);

            if (dialog.isShowing())
                dialog.dismiss();

            moviePosters = p;
            //Grid Adapter
            GridAdapter adapter = new GridAdapter(getActivity());
            Log.e("COUNT", adapter.getCount() + "");
            gridMovies.setAdapter(adapter);

            Log.e("GETTING IMAGES", gridMovies.getFirstVisiblePosition() + " to " + gridMovies.getLastVisiblePosition());
            for (int i = 0; i < moviePosters.size(); i++) {
                if (moviePosters.get(i).getPoster() == null) {
                    new GetMoviePosterThread(i).execute(moviePosters.get(i).getPath());
                    ImageView loadingImageView = new ImageView(getActivity());
                    loadingImageView.setImageResource(android.R.drawable.progress_indeterminate_horizontal);
                }
            }
        }

        protected JSONObject getConfig() throws IOException, JSONException {
            Uri builtUri = Uri.parse(MOVIEDB_CONFIG_URL).buildUpon()
                    .appendQueryParameter(API_PARAM, API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            Log.v(this.getClass().getSimpleName(), "Built URI " + builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null)
                buffer.append(line);

            if (buffer.length() == 0)
                return null;
            String jsonString = buffer.toString();

            Log.e("CONFIG", new JSONObject(jsonString).toString(4));

            return new JSONObject(jsonString);
        }

        protected JSONObject getMovies() throws IOException, JSONException {
            Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                    .appendQueryParameter(API_PARAM, API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            Log.v(this.getClass().getSimpleName(), "Built URI " + builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
                return null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null)
                buffer.append(line);

            if (buffer.length() == 0)
                return null;
            String jsonString = buffer.toString();

            Log.e("RETURN", new JSONObject(jsonString).getJSONArray("results").getJSONObject(0).toString(4));

            return new JSONObject(jsonString);
        }
    }
}
