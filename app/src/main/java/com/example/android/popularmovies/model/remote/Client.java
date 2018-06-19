package com.example.android.popularmovies.model.remote;

import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.datamodel.MovieDbResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

final class Client {
	
	private final MovieDbService movieDbService;
	
	private static Client client;
	
	static Client getInstance() {
		if (client == null) {
			client = new Client();
		}
		return client;
	}
	
	private Client() {
		movieDbService = new Retrofit.Builder()
				.baseUrl(UrlManager.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build()
				.create(MovieDbService.class);
	}
	
	
	Single<List<Movie>> getMovies(String sortBy) {
		if (invalidSortBy(sortBy)) {
			throw new IllegalArgumentException("Parameter not recognized");
		}
		return queryNetwork(sortBy);
	}
	
	private Single<List<Movie>> queryNetwork(String sortBy) {
		return movieDbService.sortedMovies(sortBy)
				.map(MovieDbResponse::getMoviesList);
	}
	
	private boolean invalidSortBy(String sortBy) {
		return !sortBy.equals(UrlManager.QUERY_POPULAR) && !sortBy.equals(UrlManager.QUERY_HIGHEST_RATED);
	}
}
