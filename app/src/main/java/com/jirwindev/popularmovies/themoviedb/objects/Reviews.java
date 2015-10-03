package com.jirwindev.popularmovies.themoviedb.objects;

/**
 * Created by josh on 9/27/15.
 */
public class Reviews {

	int            id;
	int            page;
	ReviewResult[] results;
	int            total_pages;
	int            total_results;

	public Reviews() {
	}

	public Reviews(int id, int page, ReviewResult[] results, int total_pages, int total_results) {
		this.id = id;
		this.page = page;
		this.results = results;
		this.total_pages = total_pages;
		this.total_results = total_results;
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

	public ReviewResult[] getResults() {
		return results;
	}

	public void setResults(ReviewResult[] results) {
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
