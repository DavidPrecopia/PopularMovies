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
	
	private ILocalStorage localStorage;
	private IRemoteStorage remoteStorage;
	
	private static Model model;
	
	public static Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}
	
	private Model() {
		remoteStorage = RemoteStorage.getInstance();
		localStorage = LocalStorage.getInstance();
	}
	
	
	@Override
	public Single<List<Movie>> getPopularMovies(boolean forceRefresh) {
		return remoteStorage.getPopularMovies();
//		if (shouldRefresh(forceRefresh, localStorage.havePopular())) {
//		} else {
//			return localStorage.getPopularMovies();
//		}
	}
	
	@Override
	public Single<List<Movie>> getHighestRatedMovies(boolean forceRefresh) {
		return remoteStorage.getHighestRatedMovies();
//		if (shouldRefresh(forceRefresh, localStorage.haveHighestRated())) {
//		} else {
//			return localStorage.getHighestRatedMovies();
//		}
	}
	
	private boolean shouldRefresh(boolean forceRefresh, boolean haveLocal) {
		return forceRefresh || ! haveLocal;
	}
}
