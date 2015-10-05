package com.jirwindev.popularmovies.themoviedb.objects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by josh on 10/4/15.
 */
public class Images implements Parcelable {

	String   base_url;
	String   secure_base_url;
	String[] backdrop_sizes;
	String[] logo_sizes;
	String[] poster_sizes;
	String[] profile_sizes;
	String[] still_sizes;

	public static final String BASE_URL        = "base_url";
	public static final String SECURE_BASE_URL = "secure_base_url";
	public static final String BACKDROP_SIZES  = "backdrop_sizes";
	public static final String LOGO_SIZES      = "logo_sizes";
	public static final String POSTER_SIZES    = "poster_sizes";
	public static final String PROFILE_SIZES   = "profile_sizes";
	public static final String STILL_SIZES     = "still_sizes";

	public Images() {
	}

	public Images(Parcel in) {
		Bundle data = in.readBundle();
		base_url = data.getString(BASE_URL);
		secure_base_url = data.getString(SECURE_BASE_URL);
		backdrop_sizes = data.getStringArray(BACKDROP_SIZES);
		logo_sizes = data.getStringArray(LOGO_SIZES);
		profile_sizes = data.getStringArray(PROFILE_SIZES);
		poster_sizes = data.getStringArray(POSTER_SIZES);
		still_sizes = data.getStringArray(STILL_SIZES);
	}

	public Images(String base_url, String secure_base_url, String[] backdrop_sizes,
				  String[] logo_sizes, String[] poster_sizes, String[] profile_sizes, String[] still_sizes) {
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int i) {
		Bundle args = new Bundle();
		args.putString(BASE_URL, base_url);
		args.putString(SECURE_BASE_URL, secure_base_url);
		args.putStringArray(BACKDROP_SIZES, backdrop_sizes);
		args.putStringArray(LOGO_SIZES, logo_sizes);
		args.putStringArray(PROFILE_SIZES, profile_sizes);
		args.putStringArray(POSTER_SIZES, poster_sizes);
		args.putStringArray(STILL_SIZES, still_sizes);

		out.writeBundle(args);
	}
}

