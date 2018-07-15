package com.example.android.popularmovies.model.contracts_model;

import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.model_favorites.database.FavoriteMovie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface IModelFavoritesContract {
	Flowable<List<Movie>> getFavorites();
	Single<Boolean> isFavorite(int movieId);
	
	Completable addMovie(FavoriteMovie favoriteMovie);
	Completable deleteMovie(int movieId);
}