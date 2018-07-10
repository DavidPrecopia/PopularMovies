package com.example.android.popularmovies.model.model_favorites.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FavoritesDao {
	@Query("SELECT " +
			FavoritesContract.MOVIE_ID_COLUMN + "," +
			FavoritesContract.TITLE_COLUMN + "," +
			FavoritesContract.POSTER_URL_COLUMN +
			" FROM " +
			FavoritesContract.TABLE_NAME
	)
	Flowable<List<Movie>> getFavorites();
	
	@Query("SELECT * FROM " + FavoritesContract.TABLE_NAME + " WHERE " + FavoritesContract.MOVIE_ID_COLUMN + " = :movieId")
	int isFavorite(int movieId);
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void addMovie(FavoriteMovie favoriteMovie);
	
	@Delete
	void deleteMovie(FavoriteMovie favoriteMovie);
}
