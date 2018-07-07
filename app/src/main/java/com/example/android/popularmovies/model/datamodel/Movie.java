package com.example.android.popularmovies.model.datamodel;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.ActivityUrlManager;
import com.example.android.popularmovies.activities.GlideApp;
import com.google.gson.annotations.SerializedName;

public final class Movie implements Parcelable {
	
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
	
	
	public Movie(String title, float rating, String releaseDate, String description, String posterUrl, String backdropUrl) {
		this.title = title;
		this.rating = rating;
		this.releaseDate = formatReleaseDate(releaseDate);
		this.description = description;
		this.posterUrl = posterUrl;
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
	
	public String getPosterUrl() {
		return posterUrl;
	}
	
	public String getBackdropUrl() {
		return backdropUrl;
	}
	
	
	/**
	 * PLACEHOLDER
	 */
	// TODO Check if causing memory leak
	@BindingAdapter({"android:src"})
	public static void getBackdropImage(ImageView view, String backdropUrl) {
		GlideApp.with(view)
				.load(getBackdropUrl(backdropUrl))
				.placeholder(R.drawable.black_placeholder)
				.error(R.drawable.black_placeholder)
				.into(view);
	}
	
	private static String getBackdropUrl(String backdropUrl) {
		return ActivityUrlManager.BACKDROP_URL + backdropUrl;
	}
	
	
	/**
	 * Parcelable
	 */
	public static final Creator<Movie> CREATOR = new Creator<Movie>() {
		@Override
		public Movie createFromParcel(Parcel in) {
			return new Movie(in);
		}
		
		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};
	
	protected Movie(Parcel in) {
		title = in.readString();
		rating = in.readFloat();
		releaseDate = in.readString();
		description = in.readString();
		posterUrl = in.readString();
		backdropUrl = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(title);
		dest.writeFloat(rating);
		dest.writeString(releaseDate);
		dest.writeString(description);
		dest.writeString(posterUrl);
		dest.writeString(backdropUrl);
	}
}