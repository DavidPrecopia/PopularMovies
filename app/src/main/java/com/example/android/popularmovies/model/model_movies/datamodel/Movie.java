package com.example.android.popularmovies.model.model_movies.datamodel;

import com.google.gson.annotations.SerializedName;

public final class Movie {

	private int id;
	
	@SerializedName("original_title")
	private final String title;
	
	@SerializedName("poster_path")
	private final String posterUrl;
	
	public Movie(int id, String title, String posterUrl) {
		this.id = id;
		this.title = title;
		this.posterUrl = posterUrl;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getPosterUrl() {
		return posterUrl;
	}
}