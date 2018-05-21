package com.example.android.popularmovies.contracts;

import com.example.android.popularmovies.model.Movie;

public interface IMainPresenterContract {
	void load();
	
	void onListItemClicked(Movie movie);
	
	void getPopularMovies();
	void getHighestRatedMovies();
	
	void onRefresh();
}