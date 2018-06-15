package com.example.android.popularmovies.model.remote;

final class UrlManager {
	
	static final String BASE_URL = "https://api.themoviedb.org/";
	// Relative URL
	static final String SORT_BY_URL = "3/discover/movie?api_key=" + ApiKey.apiKey;
	
	static final String QUERY = "sort_by";
	
	private static final String SORT_ORDER = ".desc";
	static final String QUERY_POPULAR = "top_rated" + SORT_ORDER;
	static final String QUERY_HIGHEST_RATED = "vote_count" + SORT_ORDER;
	
	
	private UrlManager() {
	}
}
