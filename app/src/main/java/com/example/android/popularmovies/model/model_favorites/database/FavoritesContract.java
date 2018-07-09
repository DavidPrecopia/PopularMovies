package com.example.android.popularmovies.model.model_favorites.database;

final class FavoritesContract {
	private FavoritesContract() {
	}
	
	static final String DATABASE_NAME = "favorite_movies.db";
	
	static final String TABLE_NAME = "favorite_movies";
	
	static final String MOVIE_ID_COLUMN = "movie_id";
	static final String TITLE_COLUMN = "title";
	static final String POSTER_URL_COLUMN = "poster_url";
}
