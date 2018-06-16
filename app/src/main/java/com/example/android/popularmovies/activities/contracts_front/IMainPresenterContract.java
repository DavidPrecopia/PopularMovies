package com.example.android.popularmovies.activities.contracts_front;

import com.example.android.popularmovies.model.datamodel.Movie;

public interface IMainPresenterContract {
	void start();
	void stop();
	
	void onListItemClicked(Movie movie);
	
	void getPopularMovies();
	void getHighestRatedMovies();
	
	void onRefresh();
}