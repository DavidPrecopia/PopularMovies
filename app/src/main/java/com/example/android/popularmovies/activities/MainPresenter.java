package com.example.android.popularmovies.activities;

import com.example.android.popularmovies.contracts.IMainPresenterContract;
import com.example.android.popularmovies.contracts.IMainViewContract;
import com.example.android.popularmovies.model.Movie;

final class MainPresenter implements IMainPresenterContract {
	
	private IMainViewContract view;
	
	private static final int POPULAR_SORT = 0;
	private static final int HIGHEST_RATED_SORT = 1;

	private static final int LAST_SLECTED = POPULAR_SORT;
	
	
	// TODO Replace with Dagger2
	MainPresenter(IMainViewContract activity) {
		view = activity;
	}
	
	@Override
	public void load() {
		loading();
		view.setUpView();
		getPopularMovies();
	}
	
	
	@Override
	public void onListItemClicked(Movie movie) {
		view.openSpecificMovie(movie);
	}
	
	// Invoked by respective FAB
	@Override
	public void getPopularMovies() {
	
	}
	
	// Invoked by respective FAB
	@Override
	public void getHighestRatedMovies() {
	
	}
	
	@Override
	public void onRefresh() {
		loading();
	}
	
	
	private void loading() {
		view.showLoading();
		view.hideList();
		view.hideFab();
	}
	
	private void finishedLoading() {
		view.hideLoading();
		view.showList();
		view.showFab();
	}
}
