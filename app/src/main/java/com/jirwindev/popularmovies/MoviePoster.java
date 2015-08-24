package com.jirwindev.popularmovies;

import android.graphics.Bitmap;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Wrapper class for movie data from  TheMovieDB
 */
public class MoviePoster {

    private int id;
    private URL path;
    private Bitmap poster;
    private String title;
    private String releaseDate;
    private double voteAverage;
    private double popularity;
    private String plotSynopsis;

    public static final String ID = "id";
    public static final String POSTER_PATH = "poster_path";
    public static final String TITLE = "title";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String POPULARITY = "popularity";
    public static final String VOTE_AVERAGE = "vote_average";

    public MoviePoster() {
    }

    public MoviePoster(int id) {
        this.id = id;
    }

    public MoviePoster(int id, String path, Bitmap poster) {
        this.id = id;
        this.poster = poster;
        try {
            this.path = new URL(path);
        } catch (MalformedURLException e) {
            this.path = null;
        }
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

    public URL getPath() {
        return path;
    }

    public void setPath(URL path) {
        this.path = path;
    }

    public void setPath(String path) {
        try {
            this.path = new URL(path);
        } catch (MalformedURLException e) {
            this.path = null;
        }
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
}