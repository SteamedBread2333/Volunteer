package com.shanghai.volunteer.net.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtils {
	/**
	 * get «Î«Û
	 * */
	public static String doGet(String url) {
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpClient hc = new DefaultHttpClient();
			HttpResponse ht = hc.execute(httpGet);
			if (ht.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity he = ht.getEntity();
				InputStream is = he.getContent();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				String response = "";
				String readLine = null;
				while ((readLine = br.readLine()) != null) {
					response = response + readLine;
				}
				is.close();
				br.close();
				return response;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

}
