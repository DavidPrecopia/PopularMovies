package com.example.android.popularmovies.model.remote;

import com.example.android.popularmovies.model.contracts_back.IRemoteStorage;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.datamodel.MovieDbResponse;
import com.example.android.popularmovies.model.datamodel.MovieDetails;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RemoteStorage implements IRemoteStorage {
	
	private final MovieDbService movieDbService;
	
	private static RemoteStorage remoteStorage;
	
	public static RemoteStorage getInstance() {
		if (remoteStorage == null) {
			remoteStorage = new RemoteStorage();
		}
		return remoteStorage;
	}
	
	
	private RemoteStorage() {
		movieDbService = new Retrofit.Builder()
				.baseUrl(UrlManager.BASE_URL)
//				.client(okHttpLogging())
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build()
				.create(MovieDbService.class);
		
	}
	
//	private OkHttpClient okHttpLogging() {
//		OkHttpClient.Builder builder = new OkHttpClient.Builder();
//		if (BuildConfig.DEBUG) {
//			HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//			logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//			builder.addInterceptor(logging);
//		}
//		return builder.build();
//	}
	
	
	@Override
	public Single<List<Movie>> getPopularMovies() {
		return getMovies(UrlManager.QUERY_POPULAR);
	}
	
	@Override
	public Single<List<Movie>> getHighestRatedMovies() {
		return getMovies(UrlManager.QUERY_HIGHEST_RATED);
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