package com.example.android.popularmovies.model.remote;

final class UrlManager {
	
	static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie?api_key=" + ApiKey.apiKey;
	
	static final String SORT_BY_PARAMETER = "sort";
	static final String SORT_BY_URL = "&sort_by={" + SORT_BY_PARAMETER + "}.desc";
	
	static final String SORT_BY_POPULAR = "top_rated";
	static final String SORT_BY_HIGHEST_RATED = "vote_count";
	
	
	private UrlManager() {
	}
}
