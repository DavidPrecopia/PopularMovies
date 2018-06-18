package com.example.android.popularmovies.model.local;

import com.example.android.popularmovies.model.contracts_back.ILocalStorage;
import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public final class LocalStorage implements ILocalStorage {
	
	private List<Movie> popularCache;
	private List<Movie> highestRatedCache;
	
	private static LocalStorage localStorage;
	
	public static LocalStorage getInstance() {
		if (localStorage == null) {
			localStorage = new LocalStorage();
		}
		return localStorage;
	}
	
	private LocalStorage() {
		this.popularCache = new ArrayList<>();
		this.highestRatedCache = new ArrayList<>();
	}
	
	
	@Override
	public Single<List<Movie>> getPopularMovies() {
		return Single.just(popularCache);
	}
	
	@Override
	public Single<List<Movie>> getHighestRatedMovies() {
		return Single.just(highestRatedCache);
	}
	
	@Override
	public void replacePopularMovies(List<Movie> newMovies) {
		popularCache.clear();
		popularCache = newMovies;
	}
	
	@Override
	public void replaceHighestRatedMovies(List<Movie> newMovies) {
		highestRatedCache.clear();
		highestRatedCache = newMovies;
	}
	
	
	@Override
	public boolean havePopular() {
		return !popularCache.isEmpty();
	}
	
	@Override
	public boolean haveHighestRated() {
		return !highestRatedCache.isEmpty();
	}
}
