package com.example.android.popularmovies.activities.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

final class MainViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
	
	private final Application application;
	
	/**
	 * Creates a {@code AndroidViewModelFactory}
	 * @param application an application to pass in {@link AndroidViewModel}
	 */
	MainViewModelFactory(@NonNull Application application) {
		super(application);
		this.application = application;
	}
	
	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new MainViewModel(application);
	}
}
