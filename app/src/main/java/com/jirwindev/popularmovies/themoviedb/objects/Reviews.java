package com.jirwindev.popularmovies.themoviedb.objects;

/**
 * Created by josh on 9/27/15.
 */
public class Reviews {

	int      id;
	int      page;
	Result[] results;
	int      total_pages;
	int      total_results;

	public Reviews() {
	}

	public Reviews(int id, int page, Result[] results, int total_pages, int total_results) {
		this.id = id;
		this.page = page;
		this.results = results;
		this.total_pages = total_pages;
		this.total_results = total_results;
	}

	public class Result {
		String id;
		String author;
		String content;
		String url;

		public static final String ID      = "id";
		public static final String AUTHOR  = "author";
		public static final String CONTENT = "content";
		public static final String URL     = "url";

		public Result(String id, String author, String content, String url) {
			this.id = id;
			this.author = author;
			this.content = content;
			this.url = url;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Result[] getResults() {
		return results;
	}

	public void setResults(Result[] results) {
		this.results = results;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public void setTotal_pages(int total_pages) {
		this.total_pages = total_pages;
	}

	public int getTotal_results() {
		return total_results;
	}

	public void setTotal_results(int total_results) {
		this.total_results = total_results;
	}
}
