package com.example.android.popularmovies.contracts;

import com.example.android.popularmovies.datamodel.Movie;
import com.example.android.popularmovies.datamodel.MovieDetails;

import java.util.List;

import io.reactivex.Single;

public interface INetworkContract {
	Single<List<Movie>> getPopularMovies();
	Single<List<Movie>> getHighestRatedMovies();
	Single<MovieDetails> getSingleMovie(int movieId);
}