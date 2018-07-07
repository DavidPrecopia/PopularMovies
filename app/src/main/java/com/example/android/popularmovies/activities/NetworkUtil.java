package com.example.android.popularmovies.activities;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.popularmovies.activities.contracts_front.INetworkUtilContract;

public final class NetworkUtil implements INetworkUtilContract {
	
	private final NetworkInfo networkInfo;
	
	private static NetworkUtil networkUtil;
	
	public static NetworkUtil getInstance(Application context) {
		if (networkUtil == null) {
			networkUtil = new NetworkUtil(context);
		}
		return networkUtil;
	}
	
	private NetworkUtil(Application context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		assert connectivityManager != null;
		networkInfo = connectivityManager.getActiveNetworkInfo();
	}
	
	@Override
	public boolean haveConnection() {
		return networkInfo.isConnected();
	}
}