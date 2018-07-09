package com.example.android.popularmovies.activities.network_util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkStatus implements INetworkStatusContract {
	
	private final ConnectivityManager connectivityManager;
	
	private static NetworkStatus networkUtil;
	
	public static NetworkStatus getInstance(Application context) {
		if (networkUtil == null) {
			networkUtil = new NetworkStatus(context);
		}
		return networkUtil;
	}
	
	private NetworkStatus(Application context) {
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	
	@Override
	public boolean noConnection() {
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo == null;
	}
}