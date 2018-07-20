package com.example.android.popularmovies.activities.detail;

import com.example.android.popularmovies.datamodel.MovieDetails;

final class MovieInformation {
	
	private final MovieDetails movieDetails;
	private final boolean isFavorite;
	
	MovieInformation(MovieDetails movieDetails, boolean isFavorite) {
		this.movieDetails = movieDetails;
		this.isFavorite = isFavorite;
	}
	
	
	MovieDetails getMovieDetails() {
		return movieDetails;
	}
	
	boolean isFavorite() {
		return isFavorite;
	}
}
