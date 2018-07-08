package com.example.android.popularmovies.model.model_movies.datamodel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.extra.GlideApp;
import com.example.android.popularmovies.model.extra.UrlManager;
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
	
	
	@BindingAdapter({"android:src"})
	public static void getBackdropImage(ImageView view, String posterUrl) {
		GlideApp.with(view)
				.load(UrlManager.POSTER_URL + posterUrl)
				.placeholder(R.drawable.black_placeholder)
				.error(R.drawable.black_placeholder)
				.into(view);
	}
}