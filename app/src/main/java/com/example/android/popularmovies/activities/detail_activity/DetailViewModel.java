package com.example.android.popularmovies.activities.detail_activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.GlideApp;
import com.example.android.popularmovies.activities.UrlManager;
import com.example.android.popularmovies.model.Model;
import com.example.android.popularmovies.model.contracts_back.IModelContract;
import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.Objects;

public final class DetailViewModel extends AndroidViewModel {
	
	private MutableLiveData<Movie> movie;
	
	private IModelContract model;
	private int movieId;
	
	DetailViewModel(@NonNull Application application, Movie movieId) {
		super(application);
		this.movie = new MutableLiveData<>();
//		this.movieId = movieId;
		this.movie.setValue(movieId);
		this.model = Model.getInstance();
	}
	
	public LiveData<Movie> getMovie() {
		return movie;
	}
	
	
	@BindingAdapter({"android:src"})
	public static void getBackdropImage(ImageView view, String backdropUrl) {
		GlideApp.with(view)
				.load(getBackdropUrl(backdropUrl))
				.placeholder(R.drawable.black_placeholder)
				.error(R.drawable.black_placeholder)
				.into(view);
	}
	
	private static String getBackdropUrl(String backdropUrl) {
		return UrlManager.BACKDROP_URL + backdropUrl;
	}
	
	
	/*
		How the date is formatted: yyyy-MM-dd
		Returns: dd-MM-yyyy
	 */
	public String getReleaseDate() {
		String[] dateArray = Objects.requireNonNull(movie.getValue()).getReleaseDate().split("-");
		// Android Studio recommended concatenating a String
		// instead of using StringBuilder
		return dateArray[1]
				+ "/"
				+ dateArray[2]
				+ "/"
				+ dateArray[0];
	}
	
	
	@Override
	protected void onCleared() {
		model = null;
		super.onCleared();
	}
}