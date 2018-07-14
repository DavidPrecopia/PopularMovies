package com.example.android.popularmovies.model.model_favorites.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = FavoritesContract.TABLE_NAME)
public final class FavoriteMovie {
	
	@PrimaryKey(autoGenerate = true)
	private int id;
	
	@ColumnInfo(name = FavoritesContract.MOVIE_ID_COLUMN)
	private final int movieId;
	
	@ColumnInfo(name = FavoritesContract.TITLE_COLUMN)
	private final String title;
	
	@ColumnInfo(name = FavoritesContract.POSTER_URL_COLUMN)
	private final String posterUrl;
	
	
	public FavoriteMovie(int id, int movieId, String title, String posterUrl) {
		this.id = id;
		this.movieId = movieId;
		this.title = title;
		this.posterUrl = posterUrl;
	}
	
	@Ignore
	public FavoriteMovie(int movieId, String title, String posterUrl) {
		this.movieId = movieId;
		this.title = title;
		this.posterUrl = posterUrl;
	}
	
	
	public int getId() {
		return id;
	}
	
	public int getMovieId() {
		return movieId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getPosterUrl() {
		return posterUrl;
	}
}