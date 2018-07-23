package com.example.android.popularmovies.model;

import com.example.android.popularmovies.activities.main.Movie;
import com.example.android.popularmovies.model.database.FavoriteMovie;

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