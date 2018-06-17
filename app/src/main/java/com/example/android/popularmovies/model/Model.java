package com.example.android.popularmovies.model;

import com.example.android.popularmovies.model.contracts_back.ILocalStorage;
import com.example.android.popularmovies.model.contracts_back.IModelContract;
import com.example.android.popularmovies.model.contracts_back.IRemoteStorage;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.local.LocalStorage;
import com.example.android.popularmovies.model.remote.RemoteStorage;

import java.util.List;

import io.reactivex.Single;

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
		IRemoteStorage remoteStorage = RemoteStorage.getInstance();
		localStorage = LocalStorage.getInstance();
		
		popularFromRemote = remoteStorage.getPopularMovies()
				.map(movies -> {
					localStorage.replacePopularMovies(movies);
					return movies;
				});
		
		highestRatedFromRemote = remoteStorage.getHighestRatedMovies()
				.map(movies -> {
					localStorage.replaceHighestRatedMovies(movies);
					return movies;
				});
	}
	
	
	
	@Override
	public Single<List<Movie>> getPopularMovies(boolean forceRefresh) {
		if (shouldRefresh(forceRefresh, localStorage.havePopular())) {
			return popularFromRemote;
		} else {
			return localStorage.getPopularMovies();
		}
	}
	
	@Override
	public Single<List<Movie>> getHighestRatedMovies(boolean forceRefresh) {
		if (shouldRefresh(forceRefresh, localStorage.haveHighestRated())) {
			return highestRatedFromRemote;
		} else {
			return localStorage.getHighestRatedMovies();
		}
	}
	
	
	private boolean shouldRefresh(boolean forceRefresh, boolean haveLocal) {
		return forceRefresh || ! haveLocal;
	}
}
