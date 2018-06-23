package com.example.android.popularmovies.model.remote;

import com.example.android.popularmovies.BuildConfig;

final class UrlManager {
	private UrlManager() {
	}
	
	static final String BASE_URL = "https://api.themoviedb.org/";

	/**
	 * API key hidden in local files.
	 * Instructions: https://medium.com/code-better/hiding-api-keys-from-your-android-repository-b23f5598b906
	 */
	private static final String API_KEY = "api_key=" + BuildConfig.ApiKey;
	static final String SORT_RELATIVE_URL = "3/discover/movie?" + API_KEY;
	
	static final String SORT_BY_QUERY = "sort_by";
	
	private static final String SORT_ORDER = ".desc";
	static final String QUERY_POPULAR = "popularity" + SORT_ORDER;
	static final String QUERY_HIGHEST_RATED = "vote_count" + SORT_ORDER;
}