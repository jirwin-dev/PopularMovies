package com.jirwindev.popularmovies.threads;

/**
 * Created by Josh on 8/20/2015.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.jirwindev.popularmovies.R;

import org.json.JSONObject;

public class GetMoviePosterIdsThread extends MovieDBThread {

    private ProgressDialog dialog;

    public GetMoviePosterIdsThread(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle(R.string.getting_movies_dialog);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.show();
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);

        if (json == null) {
            //display error
        } else {

        }

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

        //Remove Dialog
        if (dialog.isShowing())
            dialog.dismiss();
    }
}