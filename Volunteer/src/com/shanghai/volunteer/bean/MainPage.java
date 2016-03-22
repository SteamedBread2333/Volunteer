package com.shanghai.volunteer.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class MainPage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Active> activeArray;// 活动
	private ArrayList<News> newsArray;// 活动

	public ArrayList<Active> getActiveArray() {
		return activeArray;
	}

	public void setActiveArray(ArrayList<Active> activeArray) {
		this.activeArray = activeArray;
	}

	public ArrayList<News> getNewsArray() {
		return newsArray;
	}

	public void setNewsArray(ArrayList<News> newsArray) {
		this.newsArray = newsArray;
	}

	/**
	 * 序列化对象
	 * 
	 * @param person
	 * @return
	 * @throws IOException
	 */
	public static String serialize(MainPage mainPage) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);
		objectOutputStream.writeObject(mainPage);
		String serStr = byteArrayOutputStream.toString("ISO-8859-1");
		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		objectOutputStream.close();
		byteArrayOutputStream.close();
		return serStr;
	}

	/**
	 * 反序列化对象
	 * 
	 * @param str
	 * @return
	 * @throws StreamCorruptedException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static MainPage deSerialization(String str)
			throws StreamCorruptedException, IOException,
			ClassNotFoundException {
		String redStr = java.net.URLDecoder.decode(str, "UTF-8");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				redStr.getBytes("ISO-8859-1"));
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream);
		MainPage mainPage = (MainPage) objectInputStream.readObject();
		objectInputStream.close();
		byteArrayInputStream.close();
		return mainPage;
	}
	public static void saveMainData(MainPage mainPage,Context context){
		SharedPreferences preferences = context.getSharedPreferences("main_data",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		try {
			editor.putString("data", serialize(mainPage));
			editor.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static MainPage getMainData(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("main_data",
				Context.MODE_PRIVATE);
		MainPage mainPage = null;
		if (!TextUtils.isEmpty(preferences.getString("data", ""))) {
			//preferences.getString("moblie", "")
			try {
				mainPage = deSerialization(preferences.getString("data", ""));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return null;
			} 
		}

		return mainPage;
	}
}
