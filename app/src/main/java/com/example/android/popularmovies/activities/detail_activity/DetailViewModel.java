package com.example.android.popularmovies.activities.detail_activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.network_util.INetworkStatusContract;
import com.example.android.popularmovies.activities.network_util.NetworkStatus;
import com.example.android.popularmovies.model.contracts_model.IModelFavoritesContract;
import com.example.android.popularmovies.model.datamodel.MovieDetails;
import com.example.android.popularmovies.model.model_favorites.ModelFavorites;
import com.example.android.popularmovies.model.model_movies.ModelMovies;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

final class DetailViewModel extends AndroidViewModel {
	
	private static final String LOG_TAG = DetailViewModel.class.getSimpleName();
	
	private final MutableLiveData<MovieDetails> movieDetails;
	private final MutableLiveData<Boolean> isFavorite;
	private final MutableLiveData<String> errorMessage;
	
	private final CompositeDisposable disposable;
	private final Single<MovieInformation> zippedSingle;
	
	private final IModelFavoritesContract modelFavorites;
	
	private final INetworkStatusContract networkStatus;
	
	DetailViewModel(@NonNull Application application, int movieId) {
		super(application);
		this.movieDetails = new MutableLiveData<>();
		this.isFavorite = new MutableLiveData<>();
		this.errorMessage = new MutableLiveData<>();
		this.modelFavorites = ModelFavorites.getInstance(application);
		this.disposable = new CompositeDisposable();
		zippedSingle = Single.zip(
				ModelMovies.getInstance(application).getSingleMovie(movieId),
				modelFavorites.isFavorite(movieId),
				MovieInformation::new
		);
		this.networkStatus = NetworkStatus.getInstance(application);
		
		init();
	}
	
	private void init() {
		if (networkStatus.noConnection()) {
			errorMessage.setValue(getApplication().getString(R.string.error_no_network));
			return;
		}
		disposable.add(zippedSingle
				.subscribeOn(Schedulers.io())
		 		.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(observer())
		);
	}
	
	private DisposableSingleObserver<MovieInformation> observer() {
		return new DisposableSingleObserver<MovieInformation>() {
			@Override
			public void onSuccess(MovieInformation movieInformation) {
				DetailViewModel.this.movieDetails.setValue(movieInformation.getMovieDetails());
				DetailViewModel.this.isFavorite.setValue(movieInformation.isFavorite());
			}
			
			@Override
			public void onError(Throwable e) {
				Log.e(LOG_TAG, e.getMessage());
				errorMessage.setValue(getApplication().getString(R.string.error_generic_error));
			}
		};
	}
	
	
	void addToFavorites() {
		isFavorite.setValue(true);
		
	}
	
	void deleteFromFavorites() {
		isFavorite.setValue(false);
		
	}
	
	
	LiveData<MovieDetails> getMovieDetails() {
		return movieDetails;
	}
	
	LiveData<Boolean> getIsFavorite() {
		return isFavorite;
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