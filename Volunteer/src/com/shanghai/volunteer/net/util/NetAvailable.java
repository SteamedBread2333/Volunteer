package com.shanghai.volunteer.net.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetAvailable {
	public static final String NET_CONNECT_ERROR = "-1";
	/**
	 * ≈–∂œÕ¯¬Á «∑Òø…”√
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetAvailable(Context context) {
		boolean result = false;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo != null && netinfo.isConnected()) {
			if (netinfo.getState() == NetworkInfo.State.CONNECTED) {
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}
}
