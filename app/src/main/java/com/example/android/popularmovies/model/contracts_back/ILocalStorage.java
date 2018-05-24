package com.example.android.popularmovies.model.contracts_back;

import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.List;

public interface ILocalStorage {
	List<Movie> getPopularMovies();
	List<Movie> getHighestRatedMovies();

	void replacePopularMovies(List<Movie> newMovies);
	void replaceHighestRatedMovies(List<Movie> newMovies);
	
	boolean havePopular();
	boolean haveHighestRated();
}