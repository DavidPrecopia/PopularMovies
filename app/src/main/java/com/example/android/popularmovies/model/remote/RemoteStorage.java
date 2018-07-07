package com.example.android.popularmovies.model.remote;

import android.util.Log;

import com.example.android.popularmovies.model.contracts_back.IRemoteStorage;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.datamodel.MovieDetails;

import java.util.List;

import io.reactivex.Single;

public final class RemoteStorage implements IRemoteStorage {
	
	private final Client client;
	
	private static RemoteStorage remoteStorage;
	
	public static RemoteStorage getInstance() {
		if (remoteStorage == null) {
			remoteStorage = new RemoteStorage();
		}
		return remoteStorage;
	}
	
	private RemoteStorage() {
		client = Client.getInstance();
	}
	
	
	public Single<List<Movie>> getPopularMovies() {
		Log.d("LOG_TAG", "getPopularMovies");
		return client.getMovies(UrlManager.QUERY_POPULAR);
	}
	
	public Single<List<Movie>> getHighestRatedMovies() {
		return client.getMovies(UrlManager.QUERY_HIGHEST_RATED);
	}
	
	
	@Override
	public Single<MovieDetails> getSingleMovie(int movieId) {
		return client.getSingleMovie(movieId);
	}
}