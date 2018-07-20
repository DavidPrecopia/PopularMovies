package com.example.android.popularmovies.activities.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.contracts.IModelContract;
import com.example.android.popularmovies.contracts.INetworkContract;
import com.example.android.popularmovies.contracts.INetworkStatusContract;
import com.example.android.popularmovies.datamodel.Movie;
import com.example.android.popularmovies.model.Model;
import com.example.android.popularmovies.network.Network;
import com.example.android.popularmovies.utils.NetworkStatus;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

final class MainViewModel extends AndroidViewModel {
	
	private static final String LOG_TAG = MainViewModel.class.getSimpleName();
	
	private final MutableLiveData<List<Movie>> movies;
	private final MutableLiveData<List<Movie>> favoriteMovies;
	private final MutableLiveData<String> errorMessage;
	private final CompositeDisposable disposable;
	
	private final INetworkContract modelMovies;
	private final IModelContract modelFavorites;
	private final INetworkStatusContract networkStatus;
	
	MainViewModel(@NonNull Application application) {
		super(application);
		this.movies = new MutableLiveData<>();
		this.favoriteMovies = new MutableLiveData<>();
		this.errorMessage = new MutableLiveData<>();
		this.disposable = new CompositeDisposable();
		this.modelMovies = Network.getInstance(application);
		this.modelFavorites = Model.getInstance(application);
		this.networkStatus = NetworkStatus.getInstance(application);
		
		init();
	}
	
	
	private void init() {
		// Start observing the Favorites database
		observeFavorites();
	}
	
	
	void getPopularMovies() {
		getMoviesFromModel(modelMovies.getPopularMovies());
	}
	
	void getHighestRatedMovies() {
		getMoviesFromModel(modelMovies.getHighestRatedMovies());
	}
	
	private void getMoviesFromModel(final Single<List<Movie>> single) {
		if (networkStatus.noConnection()) {
			showError(getApplication().getString(R.string.error_no_network));
			return;
		}
		disposable.add(single
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(moviesObserver())
		);
	}
	
	private DisposableSingleObserver<List<Movie>> moviesObserver() {
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
	
	
	private void observeFavorites() {
		disposable.add(modelFavorites.getFavorites()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(favoriteMoviesSubscriber())
		);
	}
	
	private DisposableSubscriber<List<Movie>> favoriteMoviesSubscriber() {
		return new DisposableSubscriber<List<Movie>>() {
			@Override
			public void onNext(List<Movie> favoriteMovies) {
				MainViewModel.this.favoriteMovies.setValue(favoriteMovies);
			}
			
			@Override
			public void onError(Throwable e) {
				Log.e(LOG_TAG, e.getMessage());
				errorMessage.setValue(getApplication().getString(R.string.error_generic_error));
			}
			
			@Override
			public void onComplete() {
				// N/A
			}
		};
	}
	
	
	private void showError(String errorMessage) {
		this.errorMessage.setValue(errorMessage);
	}
	
	
	LiveData<List<Movie>> getMovies() {
		return movies;
	}
	
	LiveData<List<Movie>> getFavoriteMovies() {
		return favoriteMovies;
	}
	
	LiveData<String> getErrorMessage() {
		return errorMessage;
	}
	
	
	@Override
	protected void onCleared() {
		disposable.clear();
		super.onCleared();
	}
}
