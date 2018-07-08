package com.example.android.popularmovies.model.model_favorites;

import android.arch.lifecycle.LiveData;

import com.example.android.popularmovies.model.contracts_model.IModelFavoritesContract;
import com.example.android.popularmovies.model.model_favorites.database.FavoriteMovie;

import java.util.List;

public final class ModelFavorites implements IModelFavoritesContract {
	
	@Override
	public LiveData<List<FavoriteMovie>> getFavorites() {
		return null;
	}
	
	@Override
	public boolean isFavorite(int movieId) {
		return false;
	}
	
	@Override
	public void addMovie(FavoriteMovie favoriteMovie) {
	
	}
	
	@Override
	public void removeMovie(int movieId) {
	
	}
}
