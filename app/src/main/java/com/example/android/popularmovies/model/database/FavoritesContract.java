package com.example.android.popularmovies.model.database;

public final class FavoritesContract {
	private FavoritesContract() {
	}
	
	static final String DATABASE_NAME = "favorite_movies.db";
	
	public static final String TABLE_NAME = "favorite_movies";
	
	public static final String MOVIE_ID_COLUMN = "movie_id";
	public static final String TITLE_COLUMN = "title";
	public static final String POSTER_URL_COLUMN = "poster_url";
}
