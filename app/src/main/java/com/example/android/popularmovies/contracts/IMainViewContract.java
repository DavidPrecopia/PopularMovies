package com.example.android.popularmovies.contracts;

import com.example.android.popularmovies.model.Movie;

import java.util.List;

public interface IMainViewContract {
	void setUpView();
	
	void replaceData(List<Movie> newMovies);
	
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
