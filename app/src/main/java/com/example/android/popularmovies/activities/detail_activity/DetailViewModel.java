package com.example.android.popularmovies.activities.detail_activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.activities.ErrorMessages;
import com.example.android.popularmovies.activities.network_util.INetworkStatusContract;
import com.example.android.popularmovies.activities.network_util.NetworkStatus;
import com.example.android.popularmovies.model.contracts_model.IModelMovieContract;
import com.example.android.popularmovies.model.datamodel.MovieDetails;
import com.example.android.popularmovies.model.model_movies.ModelMovies;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

final class DetailViewModel extends AndroidViewModel {
	
	private static final String LOG_TAG = DetailViewModel.class.getSimpleName();
	
	private int movieId;
	private MutableLiveData<MovieDetails> movieDetails;
	private MutableLiveData<String> errorMessage;
	
	private CompositeDisposable disposable;
	
	private IModelMovieContract model;
	private INetworkStatusContract networkStatus;
	
	DetailViewModel(@NonNull Application application, int movieId) {
		super(application);
		this.movieId = movieId;

		this.movieDetails = new MutableLiveData<>();
		this.errorMessage = new MutableLiveData<>();
		
		this.disposable = new CompositeDisposable();

		this.model = ModelMovies.getInstance(application);
		this.networkStatus = NetworkStatus.getInstance(application);
		
		init();
	}
	
	private void init() {
		if (networkStatus.noConnection()) {
			errorMessage.setValue(ErrorMessages.NO_NETWORK_ERROR_MESSAGE);
			return;
		}
		
		disposable.add(
				model.getSingleMovie(movieId)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(getObserver())
		);
	}
	
	private DisposableSingleObserver<MovieDetails> getObserver() {
		return new DisposableSingleObserver<MovieDetails>() {
			@Override
			public void onSuccess(MovieDetails movieDetails) {
				DetailViewModel.this.movieDetails.setValue(movieDetails);
			}
			
			@Override
			public void onError(Throwable e) {
				Log.e(LOG_TAG, e.getMessage());
				errorMessage.setValue(ErrorMessages.GENERIC_ERROR_MESSAGE);
			}
		};
	}
	
	
	LiveData<MovieDetails> getMovieDetails() {
		return movieDetails;
	}
	
	LiveData<String> getErrorMessage() {
		return errorMessage;
	}
	
	@Override
	protected void onCleared() {
		disposable.clear();
		model = null;
		super.onCleared();
	}
}