package com.example.android.popularmovies.activities.main_activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.network_util.INetworkStatusContract;
import com.example.android.popularmovies.activities.network_util.NetworkStatus;
import com.example.android.popularmovies.model.contracts_model.IModelMovieContract;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.model_movies.ModelMovies;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

final class MainViewModel extends AndroidViewModel {
	
	private static final String LOG_TAG = MainViewModel.class.getSimpleName();
	
	private final MutableLiveData<List<Movie>> movies;
	private final MutableLiveData<String> errorMessage;
	private final CompositeDisposable disposable;
	
	private final IModelMovieContract model;
	private final INetworkStatusContract networkStatus;
	
	// Used for refreshing
	private int lastSelectedSortBy;
	private static final int POPULAR_SORT = 100;
	private static final int HIGHEST_RATED_SORT = 200;
	
	MainViewModel(@NonNull Application application) {
		super(application);
		this.movies = new MutableLiveData<>();
		this.errorMessage = new MutableLiveData<>();
		this.disposable = new CompositeDisposable();
		this.model = ModelMovies.getInstance(application);
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
				getMoviesFromModel(model.getPopularMovies());
				break;
			case HIGHEST_RATED_SORT:
				getMoviesFromModel(model.getHighestRatedMovies());
				break;
		}
	}
	
	private void getMoviesFromModel(final Single<List<Movie>> single) {
		if (networkStatus.noConnection()) {
			showError(getApplication().getString(R.string.error_no_network));
			return;
		}
		disposable.add(single
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(getObserver())
		);
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
				showError(getApplication().getString(R.string.error_generic_error));
			}
		};
	}
	
	
	LiveData<List<Movie>> getMovies() {
		return movies;
	}
	
	LiveData<String> getErrorMessage() {
		return errorMessage;
	}
	
	private void showError(String errorMessage) {
		this.errorMessage.setValue(errorMessage);
	}
	
	
	@Override
	protected void onCleared() {
		disposable.clear();
		super.onCleared();
	}
}
