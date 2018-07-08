package com.example.android.popularmovies.model.remote;

import android.app.Application;
import android.util.Log;

import com.example.android.popularmovies.model.contracts_back.IRemoteStorage;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.example.android.popularmovies.model.datamodel.MovieDbResponse;
import com.example.android.popularmovies.model.datamodel.MovieDetails;

import java.util.List;

import io.reactivex.Single;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RemoteStorage implements IRemoteStorage {
	
	private static final String LOG_TAG = RemoteStorage.class.getSimpleName();
	
	private final MovieDbService movieDbService;
	
	private static RemoteStorage remoteStorage;
	
	public static RemoteStorage getInstance(Application context) {
		if (remoteStorage == null) {
			remoteStorage = new RemoteStorage(context);
		}
		return remoteStorage;
	}
	
	
	private RemoteStorage(Application context) {
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
		
//		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//		logging.setLevel(HttpLoggingInterceptor.Level.BODY);
		
		return new OkHttpClient.Builder()
//				.addInterceptor(logging)
				.cache(cache)
				.build();
	}

	
	@Override
	public Single<List<Movie>> getPopularMovies() {
		return getMovies(UrlManager.QUERY_POPULAR);
	}
	
	@Override
	public Single<List<Movie>> getHighestRatedMovies() {
		return getMovies(UrlManager.QUERY_HIGHEST_RATED);
	}
	
	private Single<List<Movie>> getMovies(String sortBy) {
		Log.d(LOG_TAG, "getMovies");
		return movieDbService.sortedMovies(sortBy)
				.map(MovieDbResponse::getMoviesList);
	}
	
	
	@Override
	public Single<MovieDetails> getSingleMovie(int movieId) {
		return movieDbService.singleMovie(movieId);
	}
}