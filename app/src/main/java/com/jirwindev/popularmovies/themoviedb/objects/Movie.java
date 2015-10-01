package com.jirwindev.popularmovies.themoviedb.objects;

/**
 * Created by josh on 9/28/15.
 */
public class Movie {

	boolean    adult;
	String     backdrop_path;
	Collection belongs_to_collection;
	long       budget;
	Genre[]    genres;
	String     homepage;
	int        id;
	String     imdb_id;
	String     original_language;
	String     original_title;
	String     overview;
	double     popularity;
	String     poster_path;
	Company[]  production_companies;
	Country[]  production_countries;
	String     release_date;
	long       revenue;
	int        runtime;
	Language[] spoken_languages;
	String     status;
	String     tagline;
	String     title;
	boolean    video;
	float      vote_average;
	int        vote_count;

	public static final String ID           = "id";
	public static final String TITLE        = "title";
	public static final String VOTE_AVERAGE = "vote_average";
	public static final String OVERVIEW     = "overview";
	public static final String RELEASE_DATE = "release_date";
	public static final String RUNTIME      = "runtime";

	public Movie() {

	}

	public Movie(boolean adult, String backdrop_path, Collection belongs_to_collection,
				 long budget, Genre[] genres, String homepage, int id, String imdb_id, String original_language, String original_title, String overview, double popularity, String poster_path, Company[] production_companies, Country[] production_countries, String release_date, long revenue, int runtime, Language[] spoken_languages, String status, String tagline, String title, boolean video, float vote_average, int vote_count) {
		this.adult = adult;
		this.backdrop_path = backdrop_path;
		this.belongs_to_collection = belongs_to_collection;
		this.budget = budget;
		this.genres = genres;
		this.homepage = homepage;
		this.id = id;
		this.imdb_id = imdb_id;
		this.original_language = original_language;
		this.original_title = original_title;
		this.overview = overview;
		this.popularity = popularity;
		this.poster_path = poster_path;
		this.production_companies = production_companies;
		this.production_countries = production_countries;
		this.release_date = release_date;
		this.revenue = revenue;
		this.runtime = runtime;
		this.spoken_languages = spoken_languages;
		this.status = status;
		this.tagline = tagline;
		this.title = title;
		this.video = video;
		this.vote_average = vote_average;
		this.vote_count = vote_count;
	}

	public class Collection {
		int    id;
		String name, poster_path, backdrop_path;

		public Collection() {
		}

		public Collection(int id, String name, String poster_path, String backdrop_path) {
			this.id = id;
			this.name = name;
			this.poster_path = poster_path;
			this.backdrop_path = backdrop_path;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPoster_path() {
			return poster_path;
		}

		public void setPoster_path(String poster_path) {
			this.poster_path = poster_path;
		}

		public String getBackdrop_path() {
			return backdrop_path;
		}

		public void setBackdrop_path(String backdrop_path) {
			this.backdrop_path = backdrop_path;
		}
	}

	public class Genre {
		int    id;
		String name;

		public Genre() {
		}

		public Genre(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public class Company {
		int    id;
		String name;

		public Company() {
		}

		public Company(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public class Country {
		String iso_3116_1;
		String name;

		public Country() {

		}

		public Country(String iso_3116_1, String name) {
			this.iso_3116_1 = iso_3116_1;
			this.name = name;
		}

		public String getIso_3116_1() {
			return iso_3116_1;
		}

		public void setIso_3116_1(String iso_3116_1) {
			this.iso_3116_1 = iso_3116_1;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public class Language {
		String iso_639_1;
		String name;

		public Language() {

		}

		public Language(String iso_639_1, String name) {
			this.iso_639_1 = iso_639_1;
			this.name = name;
		}

		public String getIso_639_1() {
			return iso_639_1;
		}

		public void setIso_639_1(String iso_639_1) {
			this.iso_639_1 = iso_639_1;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public String getBackdrop_path() {
		return backdrop_path;
	}

	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}

	public Collection getBelongs_to_collection() {
		return belongs_to_collection;
	}

	public void setBelongs_to_collection(Collection belongs_to_collection) {
		this.belongs_to_collection = belongs_to_collection;
	}

	public long getBudget() {
		return budget;
	}

	public void setBudget(long budget) {
		this.budget = budget;
	}

	public Genre[] getGenres() {
		return genres;
	}

	public void setGenres(Genre[] genres) {
		this.genres = genres;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImdb_id() {
		return imdb_id;
	}

	public void setImdb_id(String imdb_id) {
		this.imdb_id = imdb_id;
	}

	public String getOriginal_language() {
		return original_language;
	}

	public void setOriginal_language(String original_language) {
		this.original_language = original_language;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	public Company[] getProduction_companies() {
		return production_companies;
	}

	public void setProduction_companies(Company[] production_companies) {
		this.production_companies = production_companies;
	}

	public Country[] getProduction_countries() {
		return production_countries;
	}

	public void setProduction_countries(Country[] production_countries) {
		this.production_countries = production_countries;
	}

	public String getReleaseDate() {
		return release_date;
	}

	public void setReleaseDate(String release_date) {
		this.release_date = release_date;
	}

	public long getRevenue() {
		return revenue;
	}

	public void setRevenue(long revenue) {
		this.revenue = revenue;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public Language[] getSpoken_languages() {
		return spoken_languages;
	}

	public void setSpoken_languages(Language[] spoken_languages) {
		this.spoken_languages = spoken_languages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public float getVoteAverage() {
		return vote_average;
	}

	public void setVoteAverage(float vote_average) {
		this.vote_average = vote_average;
	}

	public int getVote_count() {
		return vote_count;
	}

	public void setVote_count(int vote_count) {
		this.vote_count = vote_count;
	}
}
