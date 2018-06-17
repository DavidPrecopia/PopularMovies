package com.example.android.popularmovies.model.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * This will initially hold de-serialized list of movies from MovieDb.
 */
public final class MovieDbResponse {

	private final List<Movie> results;
	
	public MovieDbResponse() {
		this.results = new ArrayList<>();
	}
	
	public List<Movie> getResults() {
		return results;
	}
}
