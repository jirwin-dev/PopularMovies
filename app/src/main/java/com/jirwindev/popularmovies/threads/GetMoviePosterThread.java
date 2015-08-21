package com.jirwindev.popularmovies.threads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by Josh on 8/20/2015.
 */
public class GetMoviePosterThread extends AsyncTask<String, Void, Bitmap> {

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

//    @Override
//    protected void onPostExecute(Bitmap poster) {
//        super.onPostExecute(poster);
//
//        moviePosters.get(this.position).setPoster(poster);
//
//        if (position <= gridMovies.getLastVisiblePosition()) {
//            ((ImageView) gridMovies.getChildAt(position - gridMovies.getFirstVisiblePosition())).setImageBitmap(poster);
//            ((ImageView) gridMovies.getChildAt(position - gridMovies.getFirstVisiblePosition())).invalidate();
//        }
//    }

}