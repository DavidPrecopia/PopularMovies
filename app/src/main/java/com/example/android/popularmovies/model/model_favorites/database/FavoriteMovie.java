package com.example.android.popularmovies.model.model_favorites.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.extra.GlideApp;
import com.example.android.popularmovies.model.extra.UrlManager;

@Entity(tableName = FavoritesContract.TABLE_NAME)
public final class FavoriteMovie {
	
	@PrimaryKey(autoGenerate = true)
	private int id;
	
	@ColumnInfo(name = FavoritesContract.MOVIE_ID_COLUMN)
	private final int movieId;
	
	@ColumnInfo(name = FavoritesContract.TITLE_COLUMN)
	private final String title;
	
	@ColumnInfo(name = FavoritesContract.POSTER_URL_COLUMN)
	private final String posterUrl;
	
	FavoriteMovie(int id, int movieId, String title, String posterUrl) {
		this.id = id;
		this.movieId = movieId;
		this.title = title;
		this.posterUrl = posterUrl;
	}
	
	int getId() {
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
	public static void getBackdropImage(ImageView view, String posterUrl) {
		GlideApp.with(view)
				.load(UrlManager.POSTER_URL + posterUrl)
				.placeholder(R.drawable.black_placeholder)
				.error(R.drawable.black_placeholder)
				.into(view);
	}
	
}