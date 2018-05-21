package com.example.android.popularmovies.model;

import com.example.android.popularmovies.contracts.INetworkContract;

import java.util.List;

final class NetworkUtil implements INetworkContract {
	@Override
	public List<Movie> getPopularMovies() {
		return null;
	}
	
	@Override
	public List<Movie> getHighestRatedMovies() {
		return null;
	}
}
