package com.example.android.popularmovies.datamodel;

final class ImageUrlManager {
	private ImageUrlManager() {
	}
	
	private static final String BASE_URL = "http://image.tmdb.org/";
	private static final String RELATIVE_URL = "t/p/";
	
	static final String POSTER_URL = BASE_URL + RELATIVE_URL + "w500";
	static final String BACKDROP_URL = BASE_URL + RELATIVE_URL + "w780";
}
