package com.example.android.popularmovies.model.contracts_model;

import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.List;

import io.reactivex.Flowable;

public interface IModelFavoritesContract {
	Flowable<List<Movie>> getFavorites();
	boolean isFavorite(int movieId);
	void addMovie(Movie movie);
	void deleteMovie(Movie movie);
}