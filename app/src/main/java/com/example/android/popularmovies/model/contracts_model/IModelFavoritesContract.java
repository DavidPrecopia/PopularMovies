package com.example.android.popularmovies.model.contracts_model;

import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.model_favorites.database.FavoriteMovie;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface IModelFavoritesContract {
	Flowable<List<Movie>> getFavorites();
	Single<Boolean> isFavorite(int movieId);
	
	void addMovie(FavoriteMovie favoriteMovie);
	void deleteMovie(FavoriteMovie favoriteMovie);
}