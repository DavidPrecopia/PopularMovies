package com.example.android.popularmovies.model.contracts_back;

import com.example.android.popularmovies.model.Movie;

import java.util.List;

public interface IRemoteStorage {
	List<Movie> getPopularMovies();
	List<Movie> getHighestRatedMovies();
}