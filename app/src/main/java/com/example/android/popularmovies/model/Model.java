package com.example.android.popularmovies.model;

import com.example.android.popularmovies.model.contracts_back.ILocalStorage;
import com.example.android.popularmovies.model.contracts_back.IModelContract;
import com.example.android.popularmovies.model.contracts_back.IRemoteStorage;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.local.LocalStorage;
import com.example.android.popularmovies.model.remote.RemoteStorage;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public final class Model implements IModelContract {
	
	private static final String LOG_TAG = Model.class.getSimpleName();
	
	private final ILocalStorage localStorage;
	
	private final Single<List<Movie>> popularFromRemote;
	private final Single<List<Movie>> highestRatedFromRemote;
	
	private static Model model;
	
	public static Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}
	
	private Model() {
		localStorage = LocalStorage.getInstance();
		
		IRemoteStorage remoteStorage = RemoteStorage.getInstance();
		popularFromRemote = remoteStorage.getPopularMovies()
				.map(movies -> {
					localStorage.replacePopularMovies(movies);
					return movies;
				})
				.observeOn(Schedulers.io());
		highestRatedFromRemote = remoteStorage.getHighestRatedMovies()
				.map(movies -> {
					localStorage.replaceHighestRatedMovies(movies);
					return movies;
				})
				.observeOn(Schedulers.io());
	}
	
	
	@Override
	public Single<List<Movie>> getPopularMovies() {
		if (localStorage.havePopular()) {
			return localStorage.getPopularMovies();
		} else {
			return forceRefreshPopularMovies();
		}
	}
	
	@Override
	public Single<List<Movie>> getHighestRatedMovies() {
		if (localStorage.haveHighestRated()) {
			return localStorage.getHighestRatedMovies();
		} else {
			return forceRefreshHighestRatedMovies();
		}
	}
	
	
	@Override
	public Single<List<Movie>> forceRefreshPopularMovies() {
		return popularFromRemote;
	}
	
	@Override
	public Single<List<Movie>> forceRefreshHighestRatedMovies() {
		return highestRatedFromRemote;
	}
}