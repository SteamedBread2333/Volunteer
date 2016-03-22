/*
 * @(#)BaseActivity.java 2011-11-13
 *
 * Copyright 2006-2011 YiMing Wang, All Rights reserved.
 */
package com.shanghai.volunteer.activity.util;

import java.util.concurrent.Callable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shanghai.volunteer.R;

/**
 * 
 * @author FORREST
 */
public abstract class BaseActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	/** 点击界面按钮震动触感的时间 */
	public static final long VIBRATOR_DURATION = 15L;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// land do nothing is ok
		} else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// port do nothing is ok
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	public void onItemClick(AdapterView<?> adapterView, View view, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	public void onClick(View view) {
		// Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		// vibrator.vibrate(VIBRATOR_DURATION);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * @param <T>
	 *            模板参数，操作时要返回的内容
	 * @param pCallable
	 *            异步调用的操?
	 * @param pCallback
	 *            回调
	 */
	protected <T> void doAsync(final Callable<T> pCallable,
			final Callback<T> pCallback) {
		ActivityUtils.doAsync(this, null, "内容读取中，请稍候...", pCallable, pCallback,
				null, false);
	}

	protected <T> void doAsync(final CharSequence pMessage,
			Callable<T> pCallable, final Callback<T> pCallback) {
		ActivityUtils.doAsync(this, null, pMessage, pCallable, pCallback, null,
				false);
	}

	// 可取消
	protected <T> void doAsync(final CharSequence pMessage,
			Callable<T> pCallable, final Callback<T> pCallback,
			final Callback<Exception> pExceptionCallback) {
		ActivityUtils.doAsync(this, null, pMessage, pCallable, pCallback,
				pExceptionCallback, true);
	}

	protected <T> void doAsync(final CharSequence pTitle,
			final CharSequence pMessage, final Callable<T> pCallable,
			final Callback<T> pCallback) {
		ActivityUtils.doAsync(this, pTitle, pMessage, pCallable, pCallback,
				null, false);
	}

	/**
	 * Performs a task in the background, showing a {@link ProgressDialog},
	 * while the {@link Callable} is being processed.
	 * 
	 * @param <T>
	 * @param pTitleResID
	 * @param pMessageResID
	 * @param pErrorMessageResID
	 * @param pCallable
	 * @param pCallback
	 */
	protected <T> void doAsync(final int pTitleResID, final int pMessageResID,
			final Callable<T> pCallable, final Callback<T> pCallback) {
		this.doAsync(pTitleResID, pMessageResID, pCallable, pCallback, null);
	}

	/**
	 * Performs a task in the background, showing a indeterminate
	 * {@link ProgressDialog}, while the {@link Callable} is being processed.
	 * 
	 * @param <T>
	 * @param pTitleResID
	 * @param pMessageResID
	 * @param pErrorMessageResID
	 * @param pCallable
	 * @param pCallback
	 * @param pExceptionCallback
	 */
	protected <T> void doAsync(final int pTitleResID, final int pMessageResID,
			final Callable<T> pCallable, final Callback<T> pCallback,
			final Callback<Exception> pExceptionCallback) {
		ActivityUtils.doAsync(this, pTitleResID, pMessageResID, pCallable,
				pCallback, pExceptionCallback);
	}

	/**
	 * Performs a task in the background, showing a {@link ProgressDialog} with
	 * an ProgressBar, while the {@link AsyncCallable} is being processed.
	 * 
	 * @param <T>
	 * @param pTitleResID
	 * @param pMessageResID
	 * @param pErrorMessageResID
	 * @param pAsyncCallable
	 * @param pCallback
	 */
	protected <T> void doProgressAsync(final int pTitleResID,
			final ProgressCallable<T> pCallable, final Callback<T> pCallback) {
		this.doProgressAsync(pTitleResID, pCallable, pCallback, null);
	}

	/**
	 * Performs a task in the background, showing a {@link ProgressDialog} with
	 * a ProgressBar, while the {@link AsyncCallable} is being processed.
	 * 
	 * @param <T>
	 * @param pTitleResID
	 * @param pMessageResID
	 * @param pErrorMessageResID
	 * @param pAsyncCallable
	 * @param pCallback
	 * @param pExceptionCallback
	 */
	protected <T> void doProgressAsync(final int pTitleResID,
			final ProgressCallable<T> pCallable, final Callback<T> pCallback,
			final Callback<Exception> pExceptionCallback) {
		ActivityUtils.doProgressAsync(this, pTitleResID, pCallable, pCallback,
				pExceptionCallback);
	}

	/**
	 * Performs a task in the background, showing an indeterminate
	 * {@link ProgressDialog}, while the {@link AsyncCallable} is being
	 * processed.
	 * 
	 * @param <T>
	 * @param pTitleResID
	 * @param pMessageResID
	 * @param pErrorMessageResID
	 * @param pAsyncCallable
	 * @param pCallback
	 * @param pExceptionCallback
	 */
	protected <T> void doAsync(final int pTitleResID, final int pMessageResID,
			final AsyncCallable<T> pAsyncCallable, final Callback<T> pCallback,
			final Callback<Exception> pExceptionCallback) {
		ActivityUtils.doAsync(this, pTitleResID, pMessageResID, pAsyncCallable,
				pCallback, pExceptionCallback);
	}

	/**
	 * 显示toast
	 * 
	 * @param msg
	 *            显示的内容
	 */
	public final void showToast(String msg) {
		// Toast.makeText(this, msg, 1000).show();
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.shv_custom_toast, null);
		TextView textView = (TextView) view.findViewById(R.id.toast_txt_info);
		textView.setText(msg);
		Toast toast = new Toast(this);
		toast.setDuration(10000);
		toast.setView(view);

		toast.setGravity(Gravity.BOTTOM, 0, 100);
		toast.show();
	}
	
	
	/**
	 * 显示toast
	 * 
	 * @param msg
	 *            显示的内容
	 */
	public final void showToast(Integer msg) {
		// Toast.makeText(this, msg, 1000).show();
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.shv_custom_toast, null);

		TextView textView = (TextView) view.findViewById(R.id.toast_txt_info);
		textView.setText(msg);
		Toast toast = new Toast(this);
		toast.setDuration(10000);
		toast.setView(view);

		toast.setGravity(Gravity.BOTTOM, 0, 100);
		toast.show();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
}
