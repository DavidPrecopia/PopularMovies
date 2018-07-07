package com.example.android.popularmovies.activities;

final public class ActivityUrlManager {
	private ActivityUrlManager() {
	}
	
	private static final String BASE_URL = "http://image.tmdb.org/";
	private static final String RELATIVE_URL = "t/p/";
	
	public static final String POSTER_URL = BASE_URL + RELATIVE_URL + "w500";
	public static final String BACKDROP_URL = BASE_URL + RELATIVE_URL + "w780";
}
