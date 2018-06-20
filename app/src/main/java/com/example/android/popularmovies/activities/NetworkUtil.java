package com.example.android.popularmovies.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.popularmovies.activities.contracts_front.INetworkUtilContract;

final class NetworkUtil implements INetworkUtilContract {
	
	private final NetworkInfo networkInfo;
	
	NetworkUtil(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		assert connectivityManager != null;
		networkInfo = connectivityManager.getActiveNetworkInfo();
	}
	
	@Override
	public boolean haveConnection() {
		return networkInfo.isConnected();
	}
}