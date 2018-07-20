package com.example.android.popularmovies.activities.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.contracts.IModelContract;
import com.example.android.popularmovies.contracts.INetworkStatusContract;
import com.example.android.popularmovies.datamodel.FavoriteMovie;
import com.example.android.popularmovies.datamodel.MovieDetails;
import com.example.android.popularmovies.model.Model;
import com.example.android.popularmovies.network.Network;
import com.example.android.popularmovies.utils.NetworkStatus;

import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

final class DetailViewModel extends AndroidViewModel {
	
	private static final String LOG_TAG = DetailViewModel.class.getSimpleName();
	
	private final MutableLiveData<MovieDetails> movieDetails;
	private final MutableLiveData<Boolean> isFavorite;
	private final MutableLiveData<String> errorMessage;
	
	private final CompositeDisposable disposable;
	private final Single<MovieInformation> zippedSingle;
	
	private final IModelContract modelFavorites;
	
	private final INetworkStatusContract networkStatus;
	
	DetailViewModel(@NonNull Application application, int movieId) {
		super(application);
		this.movieDetails = new MutableLiveData<>();
		this.isFavorite = new MutableLiveData<>();
		this.errorMessage = new MutableLiveData<>();
		this.modelFavorites = Model.getInstance(application);
		this.disposable = new CompositeDisposable();
		zippedSingle = Single.zip(
				Network.getInstance(application).getSingleMovie(movieId),
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
				.subscribeWith(singleObserver())
		);
	}
	
	private DisposableSingleObserver<MovieInformation> singleObserver() {
		return new DisposableSingleObserver<MovieInformation>() {
			@Override
			public void onSuccess(MovieInformation movieInformation) {
				DetailViewModel.this.isFavorite.setValue(movieInformation.isFavorite());
				DetailViewModel.this.movieDetails.setValue(movieInformation.getMovieDetails());
			}
			
			@Override
			public void onError(Throwable e) {
				Log.e(LOG_TAG, e.getMessage());
				errorMessage.setValue(getApplication().getString(R.string.error_generic_message));
			}
		};
	}
	
	
	void addToFavorites() {
		isFavorite.setValue(true);
		disposable.add(
				modelFavorites.addMovie(new FavoriteMovie(Objects.requireNonNull(movieDetails.getValue())))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(completableObserver())
		);
	}
	
	void deleteFromFavorites() {
		isFavorite.setValue(false);
		disposable.add(
				modelFavorites.deleteMovie(Objects.requireNonNull(movieDetails.getValue()).getMovieId())
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribeWith(completableObserver())
		);
	}
	
	private DisposableCompletableObserver completableObserver() {
		return new DisposableCompletableObserver() {
			@Override
			public void onComplete() {
				Log.i(LOG_TAG, "DisposableCompletableObserver - onComplete");
			}
			
			@Override
			public void onError(Throwable e) {
				Log.e(LOG_TAG, e.getMessage());
			}
		};
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