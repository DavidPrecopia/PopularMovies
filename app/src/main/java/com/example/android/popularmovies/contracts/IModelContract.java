package com.example.android.popularmovies.contracts;

import com.example.android.popularmovies.datamodel.FavoriteMovie;
import com.example.android.popularmovies.datamodel.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface IModelContract {
	Flowable<List<Movie>> getFavorites();
	Single<Boolean> isFavorite(int movieId);
	
	Completable addMovie(FavoriteMovie favoriteMovie);
	Completable deleteMovie(int movieId);
}