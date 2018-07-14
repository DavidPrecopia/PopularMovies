package com.example.android.popularmovies.activities.favorites_activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.contracts_model.IModelFavoritesContract;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.model_favorites.ModelFavorites;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

final class FavoriteViewModel extends AndroidViewModel {
	
	private static final String LOG_TAG = FavoriteViewModel.class.getSimpleName();
	
	private final MutableLiveData<List<Movie>> favoriteMovies;
	private final MutableLiveData<String> errorMessage;
	
	private final CompositeDisposable disposable;
	private final IModelFavoritesContract model;
	
	FavoriteViewModel(@NonNull Application application) {
		super(application);
		this.favoriteMovies = new MutableLiveData<>();
		this.errorMessage = new MutableLiveData<>();
		this.disposable = new CompositeDisposable();
		this.model = ModelFavorites.getInstance(application);
		
		getFavorites();
	}
	
	
	private void getFavorites() {
		disposable.add(
				model.getFavorites()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(getSubscriber())
		);
	}
	
	private DisposableSubscriber<List<Movie>> getSubscriber() {
		return new DisposableSubscriber<List<Movie>>() {
			@Override
			public void onNext(List<Movie> favoriteMovies) {
				FavoriteViewModel.this.favoriteMovies.setValue(favoriteMovies);
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
