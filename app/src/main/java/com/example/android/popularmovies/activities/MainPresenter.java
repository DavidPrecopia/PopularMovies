package com.example.android.popularmovies.activities;

import android.util.Log;

import com.example.android.popularmovies.activities.contracts_front.IMainPresenterContract;
import com.example.android.popularmovies.activities.contracts_front.IMainViewContract;
import com.example.android.popularmovies.activities.contracts_front.INetworkUtilContract;
import com.example.android.popularmovies.model.Model;
import com.example.android.popularmovies.model.contracts_back.IModelContract;
import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

final class MainPresenter implements IMainPresenterContract {
	
	private static final String LOG_TAG = MainPresenter.class.getSimpleName();
	
	private static final String NO_NETWORK_ERROR_MESSAGE = "No internet connection";
	private static final String GENERIC_ERROR_MESSAGE = "Encounter an error";
	
	private IMainViewContract view;
	private IModelContract model;
	
	private final INetworkUtilContract networkUtil;
	private final CompositeDisposable disposable;
	
	private int lastSelectedSortBy;
	private static final int POPULAR_SORT = 100;
	private static final int HIGHEST_RATED_SORT = 200;
	
	MainPresenter(IMainViewContract view, INetworkUtilContract networkUtil) {
		this.view = view;
		this.model = Model.getInstance();
		this.networkUtil = networkUtil;
		this.disposable = new CompositeDisposable();
	}
	
	
	@Override
	public void getPopularMovies() {
		lastSelectedSortBy = POPULAR_SORT;
		getMovies(model.getPopularMovies(), view.getPopularTitle());
	}
	
	@Override
	public void getHighestRatedMovies() {
		lastSelectedSortBy = HIGHEST_RATED_SORT;
		getMovies(model.getHighestRatedMovies(), view.getHighestRatedTitle());
	}
	
	@Override
	public void onRefresh() {
		if (noNetworkConnection()) {
			showError(NO_NETWORK_ERROR_MESSAGE);
			return;
		}
		
		switch (lastSelectedSortBy) {
			case POPULAR_SORT:
				getMovies(model.forceRefreshPopularMovies(), view.getPopularTitle());
				break;
			case HIGHEST_RATED_SORT:
				getMovies(model.forceRefreshHighestRatedMovies(), view.getHighestRatedTitle());
				break;
		}
	}
	
	private void getMovies(final Single<List<Movie>> single, final String viewTitle) {
		hideError();
		startLoading();
		
		disposable.add(single
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(getObserver(viewTitle))
		);
	}
	
	private DisposableSingleObserver<List<Movie>> getObserver(final String viewTitle) {
		return new DisposableSingleObserver<List<Movie>>() {
			@Override
			public void onSuccess(List<Movie> movies) {
				replaceData(movies);
				setViewTitle(viewTitle);
				finishedLoading();
			}
			
			@Override
			public void onError(Throwable e) {
				Log.d(LOG_TAG, e.getMessage());
				showError(GENERIC_ERROR_MESSAGE);
			}
		};
	}
	
	private void replaceData(List<Movie> list) {
		view.replaceData(list);
	}
	
	private void setViewTitle(String title) {
		view.setTitle(title);
	}
	
	
	private void showError(String message) {
		view.hideLoading();
		view.hideFab();
		view.enableRefreshing();
		view.showError(message);
	}
	
	private void hideError() {
		view.hideError();
	}
	
	private boolean noNetworkConnection() {
		return ! networkUtil.haveConnection();
	}
	
	
	@Override
	public void onListItemClicked(Movie movie) {
		view.openSpecificMovie(movie);
	}
	
	
	private void startLoading() {
		view.showLoading();
		view.disableRefreshing();
		view.hideList();
		view.hideFab();
	}
	
	private void finishedLoading() {
		view.hideLoading();
		view.enableRefreshing();
		view.showList();
		view.showFab();
	}
	
	
	@Override
	public void start() {
		view.setUpView();
		
		if (noNetworkConnection()) {
			showError(NO_NETWORK_ERROR_MESSAGE);
		} else {
			getPopularMovies();
		}
	}
	
	@Override
	public void stop() {
		disposable.clear();
	}
	
	@Override
	public void destroy() {
		disposable.clear();
		view = null;
		model = null;
	}
}
