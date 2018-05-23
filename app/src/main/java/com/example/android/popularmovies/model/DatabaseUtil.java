package com.example.android.popularmovies.model;

import com.example.android.popularmovies.model.contracts_back.ILocalStorage;

import java.util.ArrayList;
import java.util.List;

final class DatabaseUtil implements ILocalStorage {
	
	private List<Movie> popularCache;
	private List<Movie> highestRatedCache;
	
	DatabaseUtil() {
		this.popularCache = new ArrayList<>();
		this.highestRatedCache = new ArrayList<>();
	}
	
	
	@Override
	public List<Movie> getPopularMovies() {
		return null;
	}
	
	@Override
	public List<Movie> getHighestRatedMovies() {
		return null;
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
		return ! popularCache.isEmpty();
	}
	
	@Override
	public boolean haveHighestRated() {
		return ! highestRatedCache.isEmpty();
	}
}
