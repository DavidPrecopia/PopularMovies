package com.example.android.popularmovies.model.datamodel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.model_favorites.database.FavoritesContract;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = FavoritesContract.TABLE_NAME)
public final class Movie {
	
	@PrimaryKey(autoGenerate = true)
	private int id;
	
	@SerializedName("id")
	@ColumnInfo(name = FavoritesContract.MOVIE_ID_COLUMN)
	private final int movieId;
	
	@SerializedName("original_title")
	@ColumnInfo(name = FavoritesContract.TITLE_COLUMN)
	private final String title;
	
	@SerializedName("poster_path")
	@ColumnInfo(name = FavoritesContract.POSTER_URL_COLUMN)
	private final String posterUrl;
	
	public Movie(int id, int movieId, String title, String posterUrl) {
		this.id = id;
		this.movieId = movieId;
		this.title = title;
		this.posterUrl = posterUrl;
	}
	
	
	public int getId() {
		return id;
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
				.load(UrlManager.POSTER_URL + posterUrl)
				.placeholder(R.drawable.black_placeholder)
				.error(R.drawable.black_placeholder)
				.into(imageView);
	}
}