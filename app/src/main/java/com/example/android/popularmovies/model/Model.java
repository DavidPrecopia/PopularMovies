package com.example.android.popularmovies.model;

import android.app.Application;

import com.example.android.popularmovies.activities.main.Movie;
import com.example.android.popularmovies.model.database.FavoriteMovie;
import com.example.android.popularmovies.model.database.FavoritesDao;
import com.example.android.popularmovies.model.database.FavoritesDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public final class Model implements IModelContract {
	
	private final FavoritesDao dao;
	
	private static Model model;
	
	public static Model getInstance(Application context) {
		if (model == null) {
			model = new Model(context);
		}
		return model;
	}
	
	private Model(Application context) {
		dao = FavoritesDatabase.getInstance(context).getDao();
	}
	
	
	@Override
	public Flowable<List<Movie>> getFavorites() {
		return dao.getFavorites();
	}
	
	@Override
	public Single<Boolean> isFavorite(int movieId) {
		return Single.fromCallable(() -> dao.isFavorite(movieId) > 0);
	}
	
	
	@Override
	public Completable addMovie(FavoriteMovie favoriteMovie) {
		return Completable.fromCallable(() -> dao.addMovie(favoriteMovie) > 0);
	}
	
	@Override
	public Completable deleteMovie(int movieId) {
		return Completable.fromCallable(() -> dao.deleteMovie(movieId) > 0);
	}
}
