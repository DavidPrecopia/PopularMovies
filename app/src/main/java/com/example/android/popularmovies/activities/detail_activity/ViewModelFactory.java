package com.example.android.popularmovies.activities.detail_activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.model.datamodel.Movie;

final class ViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
	
	private final Application application;
	private final Movie movieId;
	
	/**
	 * Creates a {@code AndroidViewModelFactory}
	 *
	 * @param application an application to pass in {@link AndroidViewModel}
	 */
	ViewModelFactory(@NonNull Application application, Movie movieId) {
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