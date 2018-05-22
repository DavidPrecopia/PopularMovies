package com.example.android.popularmovies.model;

import com.example.android.popularmovies.contracts.IModelContract;
import com.example.android.popularmovies.contracts.INetworkContract;

import java.util.ArrayList;
import java.util.List;

public final class Model implements IModelContract {
	
	private INetworkContract network;
	
	private List<Movie> popularCache;
	private List<Movie> highestRatedCache;
	
	public Model() {
		network = new NetworkUtil();
		this.popularCache = new ArrayList<>();
		this.highestRatedCache = new ArrayList<>();
	}
	
	
	@Override
	public List<Movie> getPopularMovies(boolean forceRefresh) {
		if (shouldRefresh(forceRefresh, popularCache)) {
			network.getPopularMovies();
		}
		return popularCache;
	}
	
	@Override
	public List<Movie> getHighestRatedMovies(boolean forceRefresh) {
		if (shouldRefresh(forceRefresh, highestRatedCache)) {
			network.getHighestRatedMovies();
		}
		return highestRatedCache;
	}
	
	private boolean shouldRefresh(boolean forceRefresh, List<Movie> list) {
		return forceRefresh || list.isEmpty();
	}
}
