package com.example.android.popularmovies.activities.contracts_front;

import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.List;

public interface IMainViewContract {
	void setUpView();
	
	void replaceData(List<Movie> newMovies);
	
	void setTitle(String title);
	String getPopularTitle();
	String getHighestRatedTitle();
	
	void showLoading();
	void hideLoading();
	
	void showError(String message);
	void hideError();
	
	void showList();
	void hideList();
	
	void showFab();
	void hideFab();
	
	void openSpecificMovie(Movie movie);
}
