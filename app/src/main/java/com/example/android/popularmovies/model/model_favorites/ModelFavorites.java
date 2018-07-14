package com.example.android.popularmovies.model.model_favorites;

import android.app.Application;

import com.example.android.popularmovies.model.contracts_model.IModelFavoritesContract;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.model_favorites.database.FavoriteMovie;
import com.example.android.popularmovies.model.model_favorites.database.FavoritesDao;
import com.example.android.popularmovies.model.model_favorites.database.FavoritesDatabase;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public final class ModelFavorites implements IModelFavoritesContract {
	
	private static final String LOG_TAG = ModelFavorites.class.getSimpleName();
	
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
	public Flowable<List<Movie>> getFavorites() {
		return dao.getFavorites();
	}
	
	@Override
	public Single<Boolean> isFavorite(int movieId) {
		return Single.fromCallable(() -> dao.isFavorite(movieId) != - 1);
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
