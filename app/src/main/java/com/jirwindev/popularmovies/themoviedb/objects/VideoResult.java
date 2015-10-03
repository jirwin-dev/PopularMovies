package com.jirwindev.popularmovies.themoviedb.objects;

/**
 * Created by josh on 10/3/15.
 */

public class VideoResult {
	String id;
	int    movie_id;
	String iso_639_1;
	String key;
	String name;
	String site;
	String size;
	String type;

	public static final String ID       = "id";
	public static final String MOVIE_ID = "movie_id";
	public static final String KEY      = "key";
	public static final String NAME     = "name";
	public static final String SITE     = "site";
	public static final String SIZE     = "size";
	public static final String TYPE     = "type";

	public VideoResult() {
	}

	public VideoResult(String id, String iso_639_1, String key, String name, String site,
					   String size, String type) {
		this.id = id;
		this.iso_639_1 = iso_639_1;
		this.key = key;
		this.name = name;
		this.site = site;
		this.size = size;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMovieId() {
		return movie_id;
	}

	public void setMovieId(int movie_id) {
		this.movie_id = movie_id;
	}

	public String getIso_639_1() {
		return iso_639_1;
	}

	public void setIso_639_1(String iso_639_1) {
		this.iso_639_1 = iso_639_1;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
