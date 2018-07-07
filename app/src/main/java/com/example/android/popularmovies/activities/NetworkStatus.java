package com.example.android.popularmovies.activities;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.popularmovies.activities.contracts_front.INetworkStatusContract;

public final class NetworkStatus implements INetworkStatusContract {
	
	private final NetworkInfo networkInfo;
	
	private static NetworkStatus networkUtil;
	
	public static NetworkStatus getInstance(Application context) {
		if (networkUtil == null) {
			networkUtil = new NetworkStatus(context);
		}
		return networkUtil;
	}
	
	private NetworkStatus(Application context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		assert connectivityManager != null;
		networkInfo = connectivityManager.getActiveNetworkInfo();
	}
	
	@Override
	public boolean haveConnection() {
		return networkInfo.isConnected();
	}
}