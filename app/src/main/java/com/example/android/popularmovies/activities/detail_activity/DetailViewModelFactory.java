package com.example.android.popularmovies.activities.detail_activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

final class DetailViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
	
	private final Application application;
	private final int movieId;
	
	/**
	 * Creates a {@code AndroidViewModelFactory}
	 * @param application an application to pass in {@link AndroidViewModel}
	 */
	DetailViewModelFactory(@NonNull Application application, int movieId) {
		super(application);
		this.application = application;
		this.movieId = movieId;
	}
	
	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new DetailViewModel(application, movieId);
	}
}