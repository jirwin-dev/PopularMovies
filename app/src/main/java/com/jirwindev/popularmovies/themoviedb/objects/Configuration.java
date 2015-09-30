package com.jirwindev.popularmovies.themoviedb.objects;

import com.jirwindev.popularmovies.database.DatabaseHandler;

/**
 * Created by josh on 9/16/15.
 */
public class Configuration extends DatabaseHandler {

	//	public String images;
	public Images   images;
	public String[] change_keys;

	public Configuration() {
		super();
	}

	public Configuration(Images images, String[] change_keys) {
		super();
		this.images = images;
		this.change_keys = change_keys;
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

}
