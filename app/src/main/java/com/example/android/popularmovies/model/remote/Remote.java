package com.example.android.popularmovies.model.remote;

import com.example.android.popularmovies.model.contracts_back.IRemoteStorage;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.datamodel.ResultsHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class Remote implements IRemoteStorage, Callback<ResultsHolder> {
	
	private final Client client;
	
	private static Remote remote;
	
	
	public static Remote getInstance() {
		if (remote == null) {
			remote = new Remote();
		}
		return remote;
	}
	
	private Remote() {
		client = Client.getInstance();
	}
	
	
	public List<Movie> getPopularMovies() {
		return client.getMovies(UrlManager.QUERY_POPULAR);
	}
	
	
	public List<Movie> getHighestRatedMovies() {
		return client.getMovies(UrlManager.QUERY_HIGHEST_RATED);
	}
	
	
	@Override
	public void onResponse(Call<ResultsHolder> call, Response<ResultsHolder> response) {
		if (response.isSuccessful()) {
			List<Movie> moviesList = response.body().getResults();
		}
	}
	
	@Override
	public void onFailure(Call<ResultsHolder> call, Throwable t) {
	
	}
}
