package com.example.android.popularmovies.network.client;

import com.example.android.popularmovies.activities.main.Movie;

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
