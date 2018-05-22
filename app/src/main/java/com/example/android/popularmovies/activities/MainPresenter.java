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
	
	private int lastSelected;
	private static final int POPULAR_SORT = 0;
	private static final int HIGHEST_RATED_SORT = 1;
	
	
	MainPresenter(IMainViewContract activity) {
		view = activity;
		model = new Model();
	}
	
	@Override
	public void load() {
		view.setUpView();
		getPopularMovies();
	}
	
	
	@Override
	public void getPopularMovies() {
		lastSelected = POPULAR_SORT;
		loading();
		replaceData(model.getPopularMovies(false));
		finishedLoading();
	}
	
	@Override
	public void getHighestRatedMovies() {
		lastSelected = HIGHEST_RATED_SORT;
		loading();
		replaceData(model.getHighestRatedMovies(false));
		finishedLoading();
	}
	
	private void replaceData(List<Movie> list) {
		view.replaceData(list);
	}
	
	
	@Override
	public void onRefresh() {
		loading();
		switch (lastSelected) {
			case POPULAR_SORT:
				replaceData(model.getPopularMovies(true));
				break;
			case HIGHEST_RATED_SORT:
				replaceData(model.getHighestRatedMovies(true));
				break;
		}
		finishedLoading();
	}
	
	
	@Override
	public void onListItemClicked(Movie movie) {
		view.openSpecificMovie(movie);
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
