package com.example.android.popularmovies.model.model_movies.remote;

import com.example.android.popularmovies.model.model_movies.datamodel.Movie;
import com.example.android.popularmovies.model.model_movies.datamodel.MovieDetails;

import java.util.List;

import io.reactivex.Single;

public interface IRemoteStorage {
	Single<List<Movie>> getPopularMovies();
	Single<List<Movie>> getHighestRatedMovies();
	Single<MovieDetails> getSingleMovie(int movieId);
}