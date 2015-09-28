package com.jirwindev.popularmovies.themoviedb.objects;

/**
 * Created by josh on 9/27/15.
 */
public class Videos {

	int      id;
	Result[] results;

	public Videos() {
	}

	public Videos(int id, Result[] results) {
		this.id = id;
		this.results = results;
	}

	public class Result {
		String id;
		String iso_639_1;
		String key;
		String name;
		String site;
		String size;
		String type;

		Result(String id, String iso_639_1, String key, String name, String site, String size, String type) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Result[] getResults() {
		return results;
	}

	public void setResults(Result[] results) {
		this.results = results;
	}
}
