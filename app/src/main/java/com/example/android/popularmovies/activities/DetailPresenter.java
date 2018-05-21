package com.example.android.popularmovies.activities;

import android.text.TextUtils;

import com.example.android.popularmovies.contracts.IDetailPresenterContract;
import com.example.android.popularmovies.contracts.IDetailViewContract;
import com.example.android.popularmovies.model.Movie;

final class DetailPresenter implements IDetailPresenterContract {
	
	private IDetailViewContract view;
	
	private Movie movie;
	
	@Override
	public void load(Movie movie) {
		this.movie = movie;
		view.setUpView();
		setValues();
	}
	
	private void setValues() {
		setTitle();
		setBackdrop();
		setUserRating();
		setReleaseDate();
		setDescription();
	}
	
	private void setTitle() {
		view.setTitle(movie.getTitle());
	}
	
	private void setBackdrop() {
		String imageUrl = TextUtils.isEmpty(movie.getBackdropUrl())
				? movie.getPosterUrl()
				: movie.getBackdropUrl();
		view.setBackdrop(imageUrl);
	}
	
	private void setUserRating() {
		// TODO How to calculate user rating?
		view.setUserRating(movie.getRating());
	}
	
	private void setReleaseDate() {
		// TODO Prepare date
		view.setReleaseDate(movie.getReleaseDate());
	}
	
	private void setDescription() {
		view.setDescription(movie.getDescription());
	}
}