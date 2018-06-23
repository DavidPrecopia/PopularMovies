package com.example.android.popularmovies.model.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

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
		this.releaseDate = releaseDate;
		this.description = description;
		this.posterUrl = posterUrl;
		this.backdropUrl = backdropUrl;
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