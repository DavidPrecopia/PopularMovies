package com.example.android.popularmovies.activities.contracts_font;

import com.example.android.popularmovies.model.Movie;

public interface IMainPresenterContract {
	void load();
	
	void onListItemClicked(Movie movie);
	
	void getPopularMovies();
	void getHighestRatedMovies();
	
	void onRefresh();
}