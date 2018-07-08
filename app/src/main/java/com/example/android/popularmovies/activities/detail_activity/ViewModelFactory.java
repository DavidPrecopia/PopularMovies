package com.example.android.popularmovies.activities.detail_activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

final class ViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
	
	private final Application application;
	private final int movieId;
	
	/**
	 * Creates a {@code AndroidViewModelFactory}
	 * @param application an application to pass in {@link AndroidViewModel}
	 */
	ViewModelFactory(@NonNull Application application, int movieId) {
		super(application);
		this.application = application;
		this.movieId = movieId;
	}
	
	@NonNull
	@Override
	public DetailViewModel create(@NonNull Class modelClass) {
		return new DetailViewModel(application, movieId);
	}
}