package com.example.android.popularmovies.model.model_movies;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.model.contracts_model.IModelMovieContract;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.datamodel.MovieDetails;
import com.example.android.popularmovies.model.model_movies.remote.IRemoteStorage;
import com.example.android.popularmovies.model.model_movies.remote.RemoteStorage;

import java.util.List;

import io.reactivex.Single;

public final class ModelMovies implements IModelMovieContract {
	
	private final IRemoteStorage remoteStorage;
	
	private final Single<List<Movie>> popularFromRemote;
	private final Single<List<Movie>> highestRatedFromRemote;
	
	private static ModelMovies modelMovies;
	
	public static ModelMovies getInstance(@NonNull Application context) {
		if (modelMovies == null) {
			modelMovies = new ModelMovies(context);
		}
		return modelMovies;
	}
	
	private ModelMovies(Application context) {
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