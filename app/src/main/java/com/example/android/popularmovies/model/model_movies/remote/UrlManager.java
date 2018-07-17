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
	static final String SORT_BY = "sort_by";
	static final String SORT_RELATIVE_URL = "3/movie/{" + SORT_BY + "}" + API_KEY;
	static final String POPULAR_SORT = "popular";
	static final String HIGHEST_RATED_SORT = "top_rated";
	
	/**
	 * Single movie
	 */
	private static final String APPENDED = "&append_to_response=videos,reviews";
	static final String SINGLE_MOVIE_ID = "SINGLE_MOVIE_ID";
	static final String SINGLE_RELATIVE_URL = "3/movie/{" + SINGLE_MOVIE_ID + "}" + API_KEY + APPENDED;
}