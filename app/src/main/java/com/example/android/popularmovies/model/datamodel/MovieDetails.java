package com.example.android.popularmovies.model.datamodel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.google.gson.annotations.SerializedName;

public final class MovieDetails {
	
	@SerializedName("original_title")
	private final String title;
	
	@SerializedName("vote_average")
	private final float rating;
	
	@SerializedName("release_date")
	private final String releaseDate;
	
	@SerializedName("overview")
	private final String description;
	
	@SerializedName("backdrop_path")
	private final String backdropUrl;
	
	
	public MovieDetails(String title, float rating, String releaseDate, String description, String backdropUrl) {
		this.title = title;
		this.rating = rating;
		this.releaseDate = formatReleaseDate(releaseDate);
		this.description = description;
		this.backdropUrl = backdropUrl;
	}
	
	/*
		How the date is formatted: yyyy-MM-dd
		Returns: dd-MM-yyyy
	 */
	private static String formatReleaseDate(String releaseDate) {
		String[] dateArray = releaseDate.split("-");
		// AS recommends concatenating a String instead of using StringBuilder
		return dateArray[1]
				+ "/"
				+ dateArray[2]
				+ "/"
				+ dateArray[0];
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
	
	public String getBackdropUrl() {
		return backdropUrl;
	}
	
	
	@BindingAdapter({"android:src"})
	public static void bindBackdropImage(ImageView imageView, String backdropUrl) {
		GlideApp.with(imageView)
				.load(UrlManager.BACKDROP_URL + backdropUrl)
				.placeholder(R.drawable.black_placeholder)
				.error(R.drawable.black_placeholder)
				.into(imageView);
	}
}
