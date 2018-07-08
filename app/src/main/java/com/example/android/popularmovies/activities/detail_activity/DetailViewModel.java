package com.example.android.popularmovies.activities.detail_activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.model.Model;
import com.example.android.popularmovies.model.contracts_back.IModelContract;
import com.example.android.popularmovies.model.datamodel.MovieDetails;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

final class DetailViewModel extends AndroidViewModel {
	
	private static final String LOG_TAG = DetailViewModel.class.getSimpleName();
	
	private MutableLiveData<MovieDetails> movieDetails;
	private int movieId;
	
	private CompositeDisposable disposable;
	
	private IModelContract model;
	
	DetailViewModel(@NonNull Application application, int movieId) {
		super(application);
		this.movieDetails = new MutableLiveData<>();
		this.movieId = movieId;
		this.disposable = new CompositeDisposable();
		this.model = Model.getInstance(application);
		
		init();
	}
	
	private void init() {
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
			}
		};
	}
	
	
	public LiveData<MovieDetails> getMovieDetails() {
		return movieDetails;
	}
	
	
	@Override
	protected void onCleared() {
		disposable.clear();
		model = null;
		super.onCleared();
	}
}