package com.jirwindev.popularmovies;

import android.graphics.Bitmap;

/**
 * Created by Josh on 8/20/2015.
 */
public class MoviePoster {

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