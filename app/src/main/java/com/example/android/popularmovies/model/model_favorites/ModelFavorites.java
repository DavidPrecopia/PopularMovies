package com.example.android.popularmovies.model.model_favorites;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.android.popularmovies.model.contracts_model.IModelFavoritesContract;
import com.example.android.popularmovies.model.model_favorites.database.FavoriteMovie;
import com.example.android.popularmovies.model.model_favorites.database.FavoritesDao;
import com.example.android.popularmovies.model.model_favorites.database.FavoritesDatabase;

import java.util.List;

public final class ModelFavorites implements IModelFavoritesContract {
	
	private final FavoritesDao dao;
	
	private static ModelFavorites modelFavorites;
	
	public static ModelFavorites getInstance(Application context) {
		if (modelFavorites == null) {
			modelFavorites = new ModelFavorites(context);
		}
		return modelFavorites;
	}
	
	private ModelFavorites(Application context) {
		dao = FavoritesDatabase.getInstance(context).getDao();
	}
	
	
	@Override
	public LiveData<List<FavoriteMovie>> getFavorites() {
		return dao.getFavorites();
	}
	
	@Override
	public boolean isFavorite(int movieId) {
		return dao.isFavorite(movieId) != - 1;
	}
	
	
	@Override
	public void addMovie(FavoriteMovie favoriteMovie) {
		dao.addMovie(favoriteMovie);
	}
	
	@Override
	public void deleteMovie(FavoriteMovie favoriteMovie) {
		dao.deleteMovie(favoriteMovie);
	}
}
