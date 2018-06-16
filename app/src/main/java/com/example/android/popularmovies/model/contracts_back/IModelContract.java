package com.example.android.popularmovies.model.contracts_back;

import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.List;

import io.reactivex.Single;

public interface IModelContract {
	Single<List<Movie>> getPopularMovies(boolean forceRefresh);
	Single<List<Movie>> getHighestRatedMovies(boolean forceRefresh);
}