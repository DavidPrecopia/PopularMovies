package com.example.android.popularmovies.network;

import com.example.android.popularmovies.activities.detail.MovieDetails;
import com.example.android.popularmovies.activities.main.Movie;

import java.util.List;

import io.reactivex.Single;

public interface INetworkContract {
	Single<List<Movie>> getPopularMovies();
	Single<List<Movie>> getHighestRatedMovies();
	Single<MovieDetails> getSingleMovie(int movieId);
}