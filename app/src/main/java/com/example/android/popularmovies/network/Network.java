package com.example.android.popularmovies.network;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.contracts.INetworkContract;
import com.example.android.popularmovies.contracts.IRemoteStorage;
import com.example.android.popularmovies.datamodel.Movie;
import com.example.android.popularmovies.datamodel.MovieDetails;
import com.example.android.popularmovies.network.client.Client;

import java.util.List;

import io.reactivex.Single;

public final class Network implements INetworkContract {
	
	private final IRemoteStorage client;
	
	private final Single<List<Movie>> popularFromRemote;
	private final Single<List<Movie>> highestRatedFromRemote;
	
	private static Network network;
	
	public static Network getInstance(@NonNull Application context) {
		if (network == null) {
			network = new Network(context);
		}
		return network;
	}
	
	private Network(Application context) {
		client = Client.getInstance(context);
		popularFromRemote = client.getPopularMovies();
		highestRatedFromRemote = client.getHighestRatedMovies();
	}
	
	
	@Override
	public Single<List<Movie>> getPopularMovies() {
		return popularFromRemote;
	}
	
	@Override
	public Single<List<Movie>> getHighestRatedMovies() {
		return highestRatedFromRemote;
	}
	
	
	@Override
	public Single<MovieDetails> getSingleMovie(int movieId) {
		return client.getSingleMovie(movieId);
	}
}