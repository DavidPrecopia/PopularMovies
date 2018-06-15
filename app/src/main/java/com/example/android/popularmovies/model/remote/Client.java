package com.example.android.popularmovies.model.remote;

import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.datamodel.ResultsHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

final class Client {
	
	private final MovieDbApi movieDbApi;
	
	private List<Movie> movieList;
	
	private static Client client;
	
	static Client getInstance() {
		if (client == null) {
			client = new Client();
		}
		return client;
	}
	
	private Client() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(UrlManager.BASE_URL + "/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		movieDbApi = retrofit.create(MovieDbApi.class);
	}
	
	
	List<Movie> getMovies(String sortBy) {
		if (invalidSortBy(sortBy)) {
			throw new IllegalArgumentException("Passed parameter not recognized");
		}
		return queryNetwork(sortBy);
	}
	
	private List<Movie> queryNetwork(String sortBy) {
		Call<ResultsHolder> call = movieDbApi.getMovies(sortBy);
		List<Movie> movieList = new ArrayList<>();
		
		call.enqueue(Remote.getInstance());
		
//		try {
//			movieList = call.execute().body().getResults();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return movieList;
	}
	
	private boolean invalidSortBy(String sortBy) {
		return ! sortBy.equals(UrlManager.QUERY_POPULAR) && ! sortBy.equals(UrlManager.QUERY_HIGHEST_RATED);
	}
}
