package com.example.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public final class Movie {

	@SerializedName("original_title")
	private String title;
	
	
	@SerializedName("vote_average")
	private float rating;
	
	@SerializedName("release_date")
	private String releaseDate;
	
	@SerializedName("overview")
	private String description;
	
	
	@SerializedName("poster_path")
	private String posterUrl;
	
	@SerializedName("backdrop_path")
	private String backdropUrl;
	
	
	Movie(String title, int rating, String releaseDate, String description, String posterUrl, String backdropUrl) {
		this.title = title;
		this.rating = rating;
		this.releaseDate = releaseDate;
		this.description = description;
		this.posterUrl = posterUrl;
		this.backdropUrl = backdropUrl;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public float getRating() {
		return rating;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getPosterUrl() {
		return posterUrl;
	}
	
	public String getBackdropUrl() {
		return backdropUrl;
	}
}