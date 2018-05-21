package com.example.android.popularmovies.contracts;

import com.example.android.popularmovies.model.Movie;

import java.util.List;

public interface INetworkContract {
	List<Movie> getPopularMovies();
	List<Movie> getHighestRatedMovies();
}
