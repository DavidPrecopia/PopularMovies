package com.example.android.popularmovies.datamodel;

import android.arch.persistence.room.ColumnInfo;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.database.FavoritesContract;
import com.google.gson.annotations.SerializedName;

/**
 * ColumnInfo annotations used to de-serialize from the database
 */
public final class Movie {
	
	@SerializedName("id")
	@ColumnInfo(name = FavoritesContract.MOVIE_ID_COLUMN)
	private final int movieId;
	
	@SerializedName("original_title")
	@ColumnInfo(name = FavoritesContract.TITLE_COLUMN)
	private final String title;
	
	@SerializedName("poster_path")
	@ColumnInfo(name = FavoritesContract.POSTER_URL_COLUMN)
	private final String posterUrl;
	
	public Movie(int movieId, String title, String posterUrl) {
		this.movieId = movieId;
		this.title = title;
		this.posterUrl = posterUrl.trim();
	}
	
	
	public int getMovieId() {
		return movieId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getPosterUrl() {
		return posterUrl;
	}
	
	
	@BindingAdapter({"android:src"})
	public static void bindPosterImage(ImageView imageView, String posterUrl) {
		GlideApp.with(imageView)
				.load(ImageUrlManager.POSTER_URL + posterUrl)
				.placeholder(R.drawable.black_placeholder)
				.error(R.drawable.black_placeholder)
				.into(imageView);
	}
}