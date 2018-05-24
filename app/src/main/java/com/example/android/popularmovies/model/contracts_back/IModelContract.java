package com.example.android.popularmovies.model.contracts_back;

import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.List;

public interface IModelContract {
	List<Movie> getPopularMovies(boolean forceRefresh);
	List<Movie> getHighestRatedMovies(boolean forceRefresh);
}
