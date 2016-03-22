package com.shanghai.volunteer.application;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.bean.Account;
import com.shanghai.volunteer.bean.MainPage;

public class SHVolunteerApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
		Constants.mAccount = getAccount();
		initImageLoader();
	}

	/** µ¥ÀýÄ£Ê½ */
	private static SHVolunteerApplication instance;

	public static SHVolunteerApplication getInstance() {

		return instance;

	}

	private void initImageLoader() {

		/*
		 * DisplayImageOptions defaultOptions = new
		 * DisplayImageOptions.Builder()
		 * .showImageForEmptyUri(R.drawable.empty_photo)
		 * .showImageOnFail(R.drawable.empty_photo).cacheInMemory(true)
		 * .cacheOnDisc(true).build(); ImageLoaderConfiguration config = new
		 * ImageLoaderConfiguration.Builder( getApplicationContext())
		 * .defaultDisplayImageOptions(defaultOptions) .discCacheSize(50 * 1024
		 * * 1024).discCacheFileCount(100) .writeDebugLogs().build();
		 * ImageLoader.getInstance().init(config);
		 */
		File cacheDir = StorageUtils.getCacheDirectory(getBaseContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getBaseContext())
				.memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions
				.diskCacheExtraOptions(480, 800, null)
				.threadPoolSize(3)
				// default
				.threadPriority(Thread.NORM_PRIORITY - 1)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
				// default
				.diskCache(new UnlimitedDiscCache(cacheDir))
				// default
				.diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
				.imageDownloader(new BaseImageDownloader(getBaseContext())) // default
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
	}

	public void saveAccount(Account account) {
		SharedPreferences preferences = getSharedPreferences("user_info",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("moblie", account.getUserPhone());
		editor.putString("password", account.getUserPW());
		editor.putString("name", account.getNickName());
		editor.putString("id", account.getUserID());
		editor.putString("Identity_Card", account.getIdNo());
		editor.putString("UserImg", account.getUserImg());
		editor.commit();
	}

	public Account getAccount() {
		SharedPreferences preferences = getSharedPreferences("user_info",
				Context.MODE_PRIVATE);
		Account accout = null;
		if (!TextUtils.isEmpty(preferences.getString("moblie", ""))) {
			accout = new Account();
			accout.setUserPhone(preferences.getString("moblie", ""));
			accout.setUserPW(preferences.getString("password", ""));
			accout.setNickName(preferences.getString("name", ""));
			accout.setUserID(preferences.getString("id", ""));
			accout.setIdNo(preferences.getString("Identity_Card", ""));
			accout.setUserImg(preferences.getString("UserImg", ""));
		}

		return accout;
	}

	public void clearAccount() {
		SharedPreferences preferences = getSharedPreferences("user_info",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
	}
}
