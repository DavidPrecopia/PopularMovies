package com.example.android.popularmovies.activities;

import android.text.TextUtils;

import com.example.android.popularmovies.activities.contracts_font.IDetailPresenterContract;
import com.example.android.popularmovies.activities.contracts_font.IDetailViewContract;
import com.example.android.popularmovies.model.datamodel.Movie;

final class DetailPresenter implements IDetailPresenterContract {
	
	private IDetailViewContract view;
	
	private Movie movie;
	
	DetailPresenter(IDetailViewContract view) {
		this.view = view;
	}
	
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
		view.setUserRating(movie.getRating());
	}
	
	private void setReleaseDate() {
		view.setReleaseDate(getFormattedDate());
	}
	
	/*
		How the date is formatted: yyyy-MM-dd
		Returns: dd-MM-yyyy
	 */
	private String getFormattedDate() {
		String[] dateArray = movie.getReleaseDate().split("-");
		return dateArray[2]
				+ ","
				+ dateArray[3]
				+ ", "
				+ dateArray[0];
	}
	
	private void setDescription() {
		view.setDescription(movie.getDescription());
	}
}