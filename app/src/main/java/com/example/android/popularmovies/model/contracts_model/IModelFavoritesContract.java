package com.example.android.popularmovies.model.contracts_model;

import android.arch.lifecycle.LiveData;

import com.example.android.popularmovies.model.model_favorites.database.FavoriteMovie;

import java.util.List;

public interface IModelFavoritesContract {
	LiveData<List<FavoriteMovie>> getFavorites();
	boolean isFavorite(int movieId);
	void addMovie(FavoriteMovie favoriteMovie);
	void deleteMovie(FavoriteMovie favoriteMovie);
}