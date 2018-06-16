package com.example.android.popularmovies.model.contracts_back;

import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.List;

import io.reactivex.Single;

public interface ILocalStorage {
	Single<List<Movie>> getPopularMovies();
	Single<List<Movie>> getHighestRatedMovies();

	void replacePopularMovies(List<Movie> newMovies);
	void replaceHighestRatedMovies(List<Movie> newMovies);
	
	boolean havePopular();
	boolean haveHighestRated();
}