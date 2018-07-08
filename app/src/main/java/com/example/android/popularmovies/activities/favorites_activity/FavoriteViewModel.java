package com.example.android.popularmovies.activities.favorites_activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

final class FavoriteViewModel extends AndroidViewModel {
	
	FavoriteViewModel(@NonNull Application application) {
		super(application);
	}
	
	
	@Override
	protected void onCleared() {
		super.onCleared();
	}
}
