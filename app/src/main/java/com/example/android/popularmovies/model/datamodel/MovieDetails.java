package com.example.android.popularmovies.model.datamodel;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MovieDetails {
	
	private static final String LOG_TAG = MovieDetails.class.getSimpleName();
	
	@SerializedName("id")
	private final int movieId;
	
	@SerializedName("original_title")
	private final String title;
	
	@SerializedName("vote_average")
	private final float rating;
	
	@SerializedName("release_date")
	private final String releaseDate;
	
	@SerializedName("overview")
	private final String description;
	
	@SerializedName("poster_path")
	private final String posterUrl;
	
	@SerializedName("backdrop_path")
	private final String backdropUrl;
	
	@SerializedName("videos")
	private final Trailer trailer;
	
	@SerializedName("reviews")
	private final MovieReviews movieReviews;
	
	
	private MovieDetails(int movieId, String title, float rating, String releaseDate, String description, String posterUrl, String backdropUrl, Trailer trailer, MovieReviews movieReviews) {
		this.movieId = movieId;
		this.title = title;
		this.rating = rating;
		this.releaseDate = releaseDate;
		this.description = description;
		this.posterUrl = posterUrl;
		this.backdropUrl = backdropUrl.trim();
		Log.d("LOG_TAG0", backdropUrl);
		this.trailer = trailer;
		this.movieReviews = movieReviews;
	}
	
	
	public int getMovieId() {
		return movieId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public float getRating() {
		return rating;
	}
	
	/**
	 * How the date is formatted: yyyy-MM-dd
	 * Returns: dd/MM/yyyy
	 */
	public String getReleaseDate() {
		String[] dateArray = this.releaseDate.split("-");
		// AS recommends concatenating a String instead of using StringBuilder
		return dateArray[1]
				+ "/"
				+ dateArray[2]
				+ "/"
				+ dateArray[0];
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
	
	public String getYouTubeTrailerId() {
		return trailer.results[0].key;
	}
	
	public List<Review> getReviews() {
		return new ArrayList<>(Arrays.asList(movieReviews.reviews));
	}
	
	
	@BindingAdapter({"android:src"})
	public static void bindBackdropImage(ImageView imageView, String backdropUrl) {
		GlideApp.with(imageView)
				.load(UrlManager.BACKDROP_URL + backdropUrl)
				.placeholder(R.drawable.black_placeholder)
				.error(R.drawable.black_placeholder)
				.into(imageView);
	}
	
	
	private class Trailer {
		private final Videos[] results;
		
		private Trailer(Videos[] results) {
			this.results = results;
		}
		
		private class Videos {
			private final String key;
			
			private Videos(String key) {
				this.key = key;
			}
		}
	}
	
	
	private class MovieReviews {
		@SerializedName("results")
		private final Review[] reviews;
		
		private MovieReviews(Review[] reviews) {
			this.reviews = reviews;
		}
	}
}