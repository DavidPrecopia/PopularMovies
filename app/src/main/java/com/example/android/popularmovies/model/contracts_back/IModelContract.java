package com.example.android.popularmovies.model.contracts_back;

import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.datamodel.MovieDetails;

import java.util.List;

import io.reactivex.Single;

public interface IModelContract {
	Single<List<Movie>> getPopularMovies();
	Single<List<Movie>> getHighestRatedMovies();
	
	Single<List<Movie>> forceRefreshPopularMovies();
	Single<List<Movie>> forceRefreshHighestRatedMovies();
	
	Single<MovieDetails> getSingleMovie(int movieId);
}