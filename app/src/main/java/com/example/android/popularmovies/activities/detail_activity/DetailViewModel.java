package com.example.android.popularmovies.activities.detail_activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.popularmovies.model.Model;
import com.example.android.popularmovies.model.contracts_back.IModelContract;
import com.example.android.popularmovies.model.datamodel.Movie;

public final class DetailViewModel extends ViewModel {
	
	private MutableLiveData<Movie> movie;
	
	private IModelContract model;
	private int movieId;
	
	DetailViewModel(Movie movieId) {
		this.movie = new MutableLiveData<>();
//		this.movieId = movieId;
		this.movie.setValue(movieId);
		this.model = Model.getInstance();
	}
	
	public LiveData<Movie> getMovie() {
		return movie;
	}
	
	
	@Override
	protected void onCleared() {
		model = null;
		super.onCleared();
	}
}