package com.example.android.popularmovies.model.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.android.popularmovies.activities.detail.MovieDetails;

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
	public FavoriteMovie(MovieDetails movieDetails) {
		this.movieId = movieDetails.getMovieId();
		this.title = movieDetails.getTitle();
		this.posterUrl = movieDetails.getPosterUrl();
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