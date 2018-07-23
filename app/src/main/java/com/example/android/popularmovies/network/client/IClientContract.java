package com.example.android.popularmovies.network.client;

import com.example.android.popularmovies.activities.detail.MovieDetails;
import com.example.android.popularmovies.activities.main.Movie;

import java.util.List;

import io.reactivex.Single;

public interface IClientContract {
	Single<List<Movie>> getPopularMovies();
	Single<List<Movie>> getHighestRatedMovies();
	Single<MovieDetails> getSingleMovie(int movieId);
}