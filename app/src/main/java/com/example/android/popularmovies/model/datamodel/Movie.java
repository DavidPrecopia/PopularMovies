package com.example.android.popularmovies.model.datamodel;

import com.google.gson.annotations.SerializedName;

public final class Movie {

	@SerializedName("original_title")
	private final String title;
	
	
//	@SerializedName("vote_average")
	private final float vote_average;
	
	@SerializedName("release_date")
	private final String releaseDate;
	
	@SerializedName("overview")
	private final String description;
	
	
	@SerializedName("poster_path")
	private final String posterUrl;
	
	@SerializedName("backdrop_path")
	private final String backdropUrl;
	
	
	public Movie(String title, int rating, String releaseDate, String description, String posterUrl, String backdropUrl) {
		this.title = title;
		this.vote_average = rating;
		this.releaseDate = releaseDate;
		this.description = description;
		this.posterUrl = posterUrl;
		this.backdropUrl = backdropUrl;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public float getRating() {
		return vote_average;
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