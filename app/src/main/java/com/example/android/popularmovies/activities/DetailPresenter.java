package com.example.android.popularmovies.activities;

import android.text.TextUtils;

import com.example.android.popularmovies.activities.contracts_front.IDetailPresenterContract;
import com.example.android.popularmovies.activities.contracts_front.IDetailViewContract;
import com.example.android.popularmovies.model.datamodel.Movie;

final class DetailPresenter implements IDetailPresenterContract {
	
	private IDetailViewContract view;
	
	private Movie movie;
	
	DetailPresenter(IDetailViewContract view) {
		this.view = view;
	}
	
	@Override
	public void start(Movie movie) {
		this.movie = movie;
		view.setUpView();
		setValues();
	}
	
	private void setValues() {
		setBackdrop();
		setTitle();
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
		view.setBackdrop(backdropUrl(imageUrl));
	}
	
	private String backdropUrl(String imageUrl) {
		return UrlManager.BACKDROP_URL + imageUrl;
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
		// Android Studio recommended concatenating a String
		// instead of using StringBuilder
		return dateArray[1]
				+ "/"
				+ dateArray[2]
				+ "/"
				+ dateArray[0];
	}
	
	private void setDescription() {
		view.setDescription(movie.getDescription());
	}
	
	
	@Override
	public void destroy() {
		view = null;
	}
}