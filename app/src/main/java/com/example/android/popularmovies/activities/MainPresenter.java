package com.example.android.popularmovies.activities;

import com.example.android.popularmovies.contracts.IMainPresenterContract;
import com.example.android.popularmovies.contracts.IMainViewContract;
import com.example.android.popularmovies.contracts.IModelContract;
import com.example.android.popularmovies.model.Model;
import com.example.android.popularmovies.model.Movie;

import java.util.List;

final class MainPresenter implements IMainPresenterContract {
	
	private IMainViewContract view;
	private IModelContract model;
	
	private int lastSelectedSortBy;
	private static final int POPULAR_SORT = 100;
	private static final int HIGHEST_RATED_SORT = 200;
	
	
	MainPresenter(IMainViewContract view) {
		this.view = view;
		model = new Model();
	}
	
	@Override
	public void load() {
		view.setUpView();
		getPopularMovies();
	}
	
	
	@Override
	public void getPopularMovies() {
		getMovies(POPULAR_SORT, false);
	}
	
	@Override
	public void getHighestRatedMovies() {
		getMovies(HIGHEST_RATED_SORT, false);
	}
	
	@Override
	public void onRefresh() {
		switch (lastSelectedSortBy) {
			case POPULAR_SORT:
				getMovies(POPULAR_SORT, true);
				break;
			case HIGHEST_RATED_SORT:
				getMovies(HIGHEST_RATED_SORT, true);
				break;
		}
	}
	
	private void getMovies(int sortBy, boolean forceRefresh) {
		lastSelectedSortBy = sortBy;
		startLoading();
		switch (sortBy) {
			case POPULAR_SORT:
				replaceData(model.getPopularMovies(forceRefresh));
				setViewTitle(view.getPopularTitle());
				break;
			case HIGHEST_RATED_SORT:
				replaceData(model.getHighestRatedMovies(forceRefresh));
				setViewTitle(view.getHighestRatedTitle());
				break;
		}
		finishedLoading();
	}
	
	private void setViewTitle(String title) {
		view.setTitle(title);
	}
	
	private void replaceData(List<Movie> list) {
		view.replaceData(list);
	}
	
	
	@Override
	public void onListItemClicked(Movie movie) {
		view.openSpecificMovie(movie);
	}
	
	
	private void startLoading() {
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
