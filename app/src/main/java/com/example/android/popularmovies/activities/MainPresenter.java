package com.example.android.popularmovies.activities;

import com.example.android.popularmovies.activities.contracts_front.IMainPresenterContract;
import com.example.android.popularmovies.activities.contracts_front.IMainViewContract;
import com.example.android.popularmovies.model.Model;
import com.example.android.popularmovies.model.contracts_back.IModelContract;
import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

final class MainPresenter implements IMainPresenterContract {
	
	private static final String LOG_TAG = MainPresenter.class.getSimpleName();
	
	private IMainViewContract view;
	private IModelContract model;
	private CompositeDisposable disposable;
	
	private int lastSelectedSortBy;
	private static final int POPULAR_SORT = 100;
	private static final int HIGHEST_RATED_SORT = 200;
	
	MainPresenter(IMainViewContract view) {
		this.view = view;
		model = Model.getInstance();
		disposable = new CompositeDisposable();
	}
	
	@Override
	public void start() {
		view.setUpView();
		getHighestRatedMovies();
	}
	
	@Override
	public void stop() {
		disposable.clear();
		view = null;
		model = null;
	}
	
	
	@Override
	public void getPopularMovies() {
		lastSelectedSortBy = POPULAR_SORT;
		getMovies(POPULAR_SORT, false);
	}
	
	@Override
	public void getHighestRatedMovies() {
		lastSelectedSortBy = HIGHEST_RATED_SORT;
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
		startLoading();
		switch (sortBy) {
			case POPULAR_SORT:
				disposable.add(
						model.getPopularMovies(forceRefresh)
								.subscribeOn(Schedulers.io())
								.observeOn(AndroidSchedulers.mainThread())
								.subscribeWith(popularObserver())
				);
				break;
			case HIGHEST_RATED_SORT:
				disposable.add(
						model.getHighestRatedMovies(forceRefresh)
								.subscribeOn(Schedulers.io())
								.observeOn(AndroidSchedulers.mainThread())
								.subscribeWith(highestRatedObserver())
				);
				break;
		}
	}

	private DisposableSingleObserver<List<Movie>> popularObserver() {
		return new DisposableSingleObserver<List<Movie>>() {
			@Override
			public void onSuccess(List<Movie> movies) {
				replaceData(movies);
				setViewTitle(view.getPopularTitle());
				finishedLoading();
			}

			@Override
			public void onError(Throwable e) {
				// Will handle prior to submission
			}
		};
	}

	private DisposableSingleObserver<List<Movie>> highestRatedObserver() {
		return new DisposableSingleObserver<List<Movie>>() {
			@Override
			public void onSuccess(List<Movie> movies) {
				replaceData(movies);
				setViewTitle(view.getHighestRatedTitle());
				finishedLoading();
			}

			@Override
			public void onError(Throwable e) {
				// Will handle prior to submission
			}
		};
	}
	
	
	private void replaceData(List<Movie> list) {
		view.replaceData(list);
	}
	
	private void setViewTitle(String title) {
		view.setTitle(title);
	}
	
	
	@Override
	public void onListItemClicked(Movie movie) {
		view.openSpecificMovie(movie);
	}
	
	
	private void startLoading() {
		view.showLoading();
//		view.hideList();
		view.hideFab();
	}
	
	private void finishedLoading() {
		view.hideLoading();
//		view.showList();
		view.showFab();
	}
}
