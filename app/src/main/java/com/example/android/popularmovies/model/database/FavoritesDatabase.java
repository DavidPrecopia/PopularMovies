package com.example.android.popularmovies.model.database;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {FavoriteMovie.class}, version = 1, exportSchema = false)
public abstract class FavoritesDatabase extends RoomDatabase {
	
	private static FavoritesDatabase favoritesDatabase;
	
	public static FavoritesDatabase getInstance(Application context) {
		if (favoritesDatabase == null) {
			favoritesDatabase = Room.databaseBuilder(
					context,
					FavoritesDatabase.class,
					FavoritesContract.DATABASE_NAME
			).build();
		}
		return favoritesDatabase;
	}
	
	public abstract FavoritesDao getDao();
}
