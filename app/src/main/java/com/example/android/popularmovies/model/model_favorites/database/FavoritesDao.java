package com.example.android.popularmovies.model.model_favorites.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoritesDao {
	@Query("SELECT * FROM " + FavoritesContract.TABLE_NAME)
	LiveData<List<FavoriteMovie>> getFavorites();
	
	@Query("SELECT * FROM " + FavoritesContract.TABLE_NAME + " WHERE " + FavoritesContract.MOVIE_ID_COLUMN + " = :movieId")
	int isFavorite(int movieId);
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void addMovie(FavoriteMovie favoriteMovie);
	
	@Delete
	void deleteMovie(FavoriteMovie favoriteMovie);
}
