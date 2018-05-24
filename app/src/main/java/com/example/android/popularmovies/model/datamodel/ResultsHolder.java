package com.example.android.popularmovies.model.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * This will initially hold de-serialized list of movies from MovieDb.
 */
public final class ResultsHolder {

	private final List<Movie> results;
	
	public ResultsHolder() {
		this.results = new ArrayList<>();
	}
	
	List<Movie> getResults() {
		return results;
	}
}
