package com.example.android.popularmovies.model.model_movies.remote;

import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.List;

/**
 * This will initially hold the de-serialized list of movies from The MovieDb.
 */
final class MovieDbResponse {
	
	private final List<Movie> results;
	
	private MovieDbResponse(List<Movie> results) {
		this.results = results;
	}
	
	List<Movie> getMoviesList() {
		return results;
	}
}
