package com.example.android.popularmovies.model.remote;

final class UrlManager {
	private UrlManager() {
	}
	
	static final String BASE_URL = "https://api.themoviedb.org/";
	
	private static final String API_KEY = "?api_key=" + ApiKey.apiKey;
	static final String SORT_RELATIVE_URL = "3/discover/movie" + API_KEY;
	
	static final String SORT_BY_QUERY_TERM = "sort_by";
	
	private static final String SORT_ORDER = ".desc";
	static final String QUERY_POPULAR = "popularity" + SORT_ORDER;
	static final String QUERY_HIGHEST_RATED = "vote_count" + SORT_ORDER;
}