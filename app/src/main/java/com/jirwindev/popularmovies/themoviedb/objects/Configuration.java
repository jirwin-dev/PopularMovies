package com.jirwindev.popularmovies.themoviedb.objects;

/**
 * Created by josh on 9/16/15.
 */
public class Configuration {

	//	public String images;
	public Images   images;
	public String[] change_keys;

	public Configuration() {
	}

	public class Images {
		String   base_url;
		String   secure_base_url;
		String[] backdrop_sizes;
		String[] logo_sizes;
		String[] poster_sizes;
		String[] profile_sizes;
		String[] still_sizes;

		Images(String base_url, String secure_base_url, String[] backdrop_sizes, String[] logo_sizes, String[] poster_sizes, String[] profile_sizes, String[] still_sizes) {
			this.base_url = base_url;
			this.secure_base_url = secure_base_url;
			this.backdrop_sizes = backdrop_sizes;
			this.logo_sizes = logo_sizes;
			this.poster_sizes = poster_sizes;
			this.profile_sizes = profile_sizes;
			this.still_sizes = still_sizes;
		}

		public String getBase_url() {
			return base_url;
		}

		public void setBase_url(String base_url) {
			this.base_url = base_url;
		}

		public String getSecure_base_url() {
			return secure_base_url;
		}

		public void setSecure_base_url(String secure_base_url) {
			this.secure_base_url = secure_base_url;
		}

		public String[] getBackdrop_sizes() {
			return backdrop_sizes;
		}

		public void setBackdrop_sizes(String[] backdrop_sizes) {
			this.backdrop_sizes = backdrop_sizes;
		}

		public String[] getLogo_sizes() {
			return logo_sizes;
		}

		public void setLogo_sizes(String[] logo_sizes) {
			this.logo_sizes = logo_sizes;
		}

		public String[] getPoster_sizes() {
			return poster_sizes;
		}

		public void setPoster_sizes(String[] poster_sizes) {
			this.poster_sizes = poster_sizes;
		}

		public String[] getProfile_sizes() {
			return profile_sizes;
		}

		public void setProfile_sizes(String[] profile_sizes) {
			this.profile_sizes = profile_sizes;
		}

		public String[] getStill_sizes() {
			return still_sizes;
		}

		public void setStill_sizes(String[] still_sizes) {
			this.still_sizes = still_sizes;
		}
	}


	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	public String[] getChange_keys() {
		return change_keys;
	}

	public void setChange_keys(String[] change_keys) {
		this.change_keys = change_keys;
	}

//	{
//		"images": {
//		"base_url": "http:\/\/image.tmdb.org\/t\/p\/",
//				"secure_base_url": "https:\/\/image.tmdb.org\/t\/p\/",
//				"backdrop_sizes": [
//		"w300",
//				"w780",
//				"w1280",
//				"original"
//		],
//		"logo_sizes": [
//		"w45",
//				"w92",
//				"w154",
//				"w185",
//				"w300",
//				"w500",
//				"original"
//		],
//		"poster_sizes": [
//		"w92",
//				"w154",
//				"w185",
//				"w342",
//				"w500",
//				"w780",
//				"original"
//		],
//		"profile_sizes": [
//		"w45",
//				"w185",
//				"h632",
//				"original"
//		],
//		"still_sizes": [
//		"w92",
//				"w185",
//				"w300",
//				"original"
//		]
//	},
//		"change_keys": [
//		"adult",
//				"air_date",
//				"also_known_as",
//				"alternative_titles",
//				"biography",
//				"birthday",
//				"budget",
//				"cast",
//				"certifications",
//				"character_names",
//				"created_by",
//				"crew",
//				"deathday",
//				"episode",
//				"episode_number",
//				"episode_run_time",
//				"freebase_id",
//				"freebase_mid",
//				"general",
//				"genres",
//				"guest_stars",
//				"homepage",
//				"images",
//				"imdb_id",
//				"languages",
//				"name",
//				"network",
//				"origin_country",
//				"original_name",
//				"original_title",
//				"overview",
//				"parts",
//				"place_of_birth",
//				"plot_keywords",
//				"production_code",
//				"production_companies",
//				"production_countries",
//				"releases",
//				"revenue",
//				"runtime",
//				"season",
//				"season_number",
//				"season_regular",
//				"spoken_languages",
//				"status",
//				"tagline",
//				"title",
//				"translations",
//				"tvdb_id",
//				"tvrage_id",
//				"type",
//				"video",
//				"videos"
//		]
//	}
}
