package com.example.android.popularmovies.model;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.model.contracts_back.IModelContract;
import com.example.android.popularmovies.model.contracts_back.IRemoteStorage;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.datamodel.MovieDetails;
import com.example.android.popularmovies.model.remote.RemoteStorage;

import java.util.List;

import io.reactivex.Single;

public final class Model implements IModelContract {
	
	private final IRemoteStorage remoteStorage;
	
	private final Single<List<Movie>> popularFromRemote;
	private final Single<List<Movie>> highestRatedFromRemote;
	
	private static Model model;
	
	public static Model getInstance(@NonNull Application context) {
		if (model == null) {
			model = new Model(context);
		}
		return model;
	}
	
	private Model(Application context) {
		remoteStorage = RemoteStorage.getInstance(context);
		
		popularFromRemote = remoteStorage.getPopularMovies();
		highestRatedFromRemote = remoteStorage.getHighestRatedMovies();
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
		return remoteStorage.getSingleMovie(movieId);
	}
}