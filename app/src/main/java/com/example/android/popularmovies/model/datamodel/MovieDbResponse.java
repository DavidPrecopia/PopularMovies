package com.example.android.popularmovies.model.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * This will initially hold the de-serialized list of movies from The MovieDb.
 */
public final class MovieDbResponse {

	private final List<Movie> results;
	
	public MovieDbResponse() {
		this.results = new ArrayList<>();
	}
	
	public List<Movie> getMoviesList() {
		return results;
	}
}