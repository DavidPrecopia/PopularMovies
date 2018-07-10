package com.example.android.popularmovies.model.contracts_model;

import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.datamodel.MovieDetails;

import java.util.List;

import io.reactivex.Single;

public interface IModelMovieContract {
	Single<List<Movie>> getPopularMovies();
	Single<List<Movie>> getHighestRatedMovies();
	Single<MovieDetails> getSingleMovie(int movieId);
}