package com.example.android.popularmovies.model.contracts_back;

import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.List;

public interface IRemoteStorage {
	List<Movie> getPopularMovies();
	List<Movie> getHighestRatedMovies();
}