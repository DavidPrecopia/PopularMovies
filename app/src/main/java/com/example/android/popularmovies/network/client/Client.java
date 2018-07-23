package com.example.android.popularmovies.network.client;

import android.app.Application;

import com.example.android.popularmovies.activities.detail.MovieDetails;
import com.example.android.popularmovies.activities.main.Movie;

import java.util.List;

import io.reactivex.Single;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Client implements IClientContract {
	
	private final MovieDbService movieDbService;
	
	private static Client client;
	
	public static Client getInstance(Application context) {
		if (client == null) {
			client = new Client(context);
		}
		return client;
	}
	
	
	private Client(Application context) {
		movieDbService = new Retrofit.Builder()
				.baseUrl(UrlManager.BASE_URL)
				.client(okHttpClient(context))
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build()
				.create(MovieDbService.class);
		
	}
	
	private OkHttpClient okHttpClient(Application context) {
		Cache cache = new Cache(context.getCacheDir(), 10 * 1024 * 1024);
		return new OkHttpClient.Builder()
				.cache(cache)
				.build();
	}

	
	@Override
	public Single<List<Movie>> getPopularMovies() {
		return getMovies(UrlManager.POPULAR_SORT);
	}
	
	@Override
	public Single<List<Movie>> getHighestRatedMovies() {
		return getMovies(UrlManager.HIGHEST_RATED_SORT);
	}
	
	private Single<List<Movie>> getMovies(String sortBy) {
		return movieDbService.sortedMovies(sortBy)
				.map(MovieDbResponse::getMoviesList);
	}
	
	
	@Override
	public Single<MovieDetails> getSingleMovie(int movieId) {
		return movieDbService.singleMovie(movieId);
	}
}