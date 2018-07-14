package com.example.android.popularmovies.model.model_movies.remote;

import com.example.android.popularmovies.BuildConfig;

final class UrlManager {
	private UrlManager() {
	}
	
	static final String BASE_URL = "https://api.themoviedb.org/";
	
	
	/**
	 * API key hidden in local files.
	 * Instructions: https://medium.com/code-better/hiding-api-keys-from-your-android-repository-b23f5598b906
	 */
	private static final String API_KEY = "?api_key=" + BuildConfig.ApiKey;
	
	
	/**
	 * Sorted list of movies
	 */
	static final String SORT_RELATIVE_URL = "3/discover/movie" + API_KEY;
	
	static final String SORT_BY_QUERY = "sort_by";
	
	private static final String SORT_ORDER = ".desc";
	static final String QUERY_POPULAR = "popularity" + SORT_ORDER;
	static final String QUERY_HIGHEST_RATED = "vote_count" + SORT_ORDER;
	
	
	/**
	 * Single movie
	 */
	private static final String APPENDED = "&append_to_response=videos,reviews";
	static final String SINGLE_MOVIE_ID = "SINGLE_MOVIE_ID";
	static final String SINGLE_RELATIVE_URL = "3/movie/{" + SINGLE_MOVIE_ID + "}" + API_KEY + APPENDED;
}