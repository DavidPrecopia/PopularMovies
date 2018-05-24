package com.example.android.popularmovies.model;

import com.example.android.popularmovies.model.contracts_back.ILocalStorage;
import com.example.android.popularmovies.model.contracts_back.IModelContract;
import com.example.android.popularmovies.model.contracts_back.IRemoteStorage;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.local.Local;
import com.example.android.popularmovies.model.remote.Remote;

import java.util.List;

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
		remoteStorage = new Remote();
		localStorage = new Local();
	}
	
	
	@Override
	public List<Movie> getPopularMovies(boolean forceRefresh) {
		if (shouldRefresh(forceRefresh, localStorage.havePopular())) {
			remoteStorage.getPopularMovies();
		}
		return localStorage.getPopularMovies();
	}
	
	@Override
	public List<Movie> getHighestRatedMovies(boolean forceRefresh) {
		if (shouldRefresh(forceRefresh, localStorage.haveHighestRated())) {
			remoteStorage.getHighestRatedMovies();
		}
		return localStorage.getHighestRatedMovies();
	}
	
	private boolean shouldRefresh(boolean forceRefresh, boolean haveLocal) {
		return forceRefresh || ! haveLocal;
	}
}
