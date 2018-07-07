package com.example.android.popularmovies.activities.detail_activity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.model.datamodel.Movie;

final class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
	
	private final Movie movieId;
	
	ViewModelFactory(Movie movieId) {
		this.movieId = movieId;
	}
	
	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new DetailViewModel(movieId);
	}
}