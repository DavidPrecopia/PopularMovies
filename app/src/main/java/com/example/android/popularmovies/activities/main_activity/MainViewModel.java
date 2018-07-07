package com.example.android.popularmovies.activities.main_activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.activities.NetworkStatus;
import com.example.android.popularmovies.activities.contracts_front.INetworkStatusContract;
import com.example.android.popularmovies.model.Model;
import com.example.android.popularmovies.model.contracts_back.IModelContract;
import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

final class MainViewModel extends AndroidViewModel {
	
	private static final String LOG_TAG = MainViewModel.class.getSimpleName();
	
	private MutableLiveData<List<Movie>> movies;
	private MutableLiveData<String> errorMessage;
	private CompositeDisposable disposable;
	
	private IModelContract model;
	private INetworkStatusContract networkStatus;
	
	// Used for refreshing
	private int lastSelectedSortBy;
	private static final int POPULAR_SORT = 100;
	private static final int HIGHEST_RATED_SORT = 200;
	
	private static final String NO_NETWORK_ERROR_MESSAGE = "No internet connection";
	private static final String GENERIC_ERROR_MESSAGE = "Encounter an error";
	
	MainViewModel(@NonNull Application application) {
		super(application);
		this.movies = new MutableLiveData<>();
		this.errorMessage = new MutableLiveData<>();
		this.disposable = new CompositeDisposable();
		this.model = Model.getInstance();
		this.networkStatus = NetworkStatus.getInstance(application);
		
		init();
	}
	
	
	private void init() {
		getPopularMovies();
	}
	
	
	void getPopularMovies() {
		lastSelectedSortBy = POPULAR_SORT;
		getMoviesFromModel(model.getPopularMovies());
	}
	
	void getHighestRatedMovies() {
		lastSelectedSortBy = HIGHEST_RATED_SORT;
		getMoviesFromModel(model.getHighestRatedMovies());
	}
	
	void onRefresh() {
		switch (lastSelectedSortBy) {
			case POPULAR_SORT:
				getMoviesFromModel(model.forceRefreshPopularMovies());
				break;
			case HIGHEST_RATED_SORT:
				getMoviesFromModel(model.forceRefreshHighestRatedMovies());
				break;
		}
	}
	
	private void getMoviesFromModel(final Single<List<Movie>> single) {
		if (noNetworkConnection()) {
			showError(NO_NETWORK_ERROR_MESSAGE);
			return;
		}
		disposable.add(
				single
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribeWith(getObserver())
		);
	}
	
	
	private void showError(String errorMessage) {
		this.errorMessage.setValue(errorMessage);
	}
	
	
	private DisposableSingleObserver<List<Movie>> getObserver() {
		return new DisposableSingleObserver<List<Movie>>() {
			@Override
			public void onSuccess(List<Movie> movies) {
				MainViewModel.this.movies.setValue(movies);
			}
			
			@Override
			public void onError(Throwable e) {
				Log.d(LOG_TAG, e.getMessage());
				showError(GENERIC_ERROR_MESSAGE);
			}
		};
	}
	
	
	LiveData<List<Movie>> getMovies() {
		return movies;
	}
	
	LiveData<String> getErrorMessage() {
		return errorMessage;
	}
	
	
	private boolean noNetworkConnection() {
		return ! networkStatus.haveConnection();
	}
	
	
	@Override
	protected void onCleared() {
		disposable.clear();
		model = null;
		super.onCleared();
	}
}
