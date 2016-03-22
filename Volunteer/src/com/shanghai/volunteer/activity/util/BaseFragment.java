package com.shanghai.volunteer.activity.util;

import java.util.concurrent.Callable;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.shanghai.volunteer.R;

public class BaseFragment extends Fragment implements OnItemClickListener , OnClickListener{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
		ActivityUtils.doAsync(getActivity(), null, "内容读取中，请稍候...", pCallable, pCallback,
				null, false);
	}

	protected <T> void doAsync(final CharSequence pMessage,
			Callable<T> pCallable, final Callback<T> pCallback) {
		ActivityUtils.doAsync(getActivity(), null, pMessage, pCallable, pCallback, null,
				false);
	}

	// 可取消
	protected <T> void doAsync(final CharSequence pMessage,
			Callable<T> pCallable, final Callback<T> pCallback,
			final Callback<Exception> pExceptionCallback) {
		ActivityUtils.doAsync(getActivity(), null, pMessage, pCallable, pCallback,
				pExceptionCallback, true);
	}

	protected <T> void doAsync(final CharSequence pTitle,
			final CharSequence pMessage, final Callable<T> pCallable,
			final Callback<T> pCallback) {
		ActivityUtils.doAsync(getActivity(), pTitle, pMessage, pCallable, pCallback,
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
		ActivityUtils.doAsync(getActivity(), pTitleResID, pMessageResID, pCallable,
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
		ActivityUtils.doProgressAsync(getActivity(), pTitleResID, pCallable, pCallback,
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
		ActivityUtils.doAsync(getActivity(), pTitleResID, pMessageResID, pAsyncCallable,
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
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.shv_custom_toast, null);
		TextView textView = (TextView) view.findViewById(R.id.toast_txt_info);
		textView.setText(msg);
		Toast toast = new Toast(getActivity());
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
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.shv_custom_toast, null);

		TextView textView = (TextView) view.findViewById(R.id.toast_txt_info);
		textView.setText(msg);
		Toast toast = new Toast(getActivity());
		toast.setDuration(10000);
		toast.setView(view);

		toast.setGravity(Gravity.BOTTOM, 0, 100);
		toast.show();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
