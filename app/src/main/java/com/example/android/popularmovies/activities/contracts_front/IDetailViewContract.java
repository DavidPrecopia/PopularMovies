package com.example.android.popularmovies.activities.contracts_front;

public interface IDetailViewContract {
	void setUpView();
	
	void setTitle(String title);
	void setBackdrop(String imageUrl);
	void setUserRating(float rating);
	void setReleaseDate(String date);
	void setDescription(String description);
}