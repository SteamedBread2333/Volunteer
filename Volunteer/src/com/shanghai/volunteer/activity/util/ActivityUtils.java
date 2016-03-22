/*
 * @(#)ActivityUtils.java 2011-11-11
 *
 * Copyright 2006-2011 YiMing Wang, All Rights reserved.
 */
package com.shanghai.volunteer.activity.util;

import java.util.concurrent.Callable;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shanghai.volunteer.R;

/**
 * 
 * @author wang
 */
public class ActivityUtils {

	public static <T> void doAsync(final Context pContext,
			final int pTitleResID, final int pMessageResID,
			final Callable<T> pCallable, final Callback<T> pCallback) {
		ActivityUtils.doAsync(pContext, pTitleResID, pMessageResID, pCallable,
				pCallback, null, false);
	}

	public static <T> void doAsync(final Context pContext,
			final CharSequence pTitle, final CharSequence pMessage,
			final Callable<T> pCallable, final Callback<T> pCallback) {
		ActivityUtils.doAsync(pContext, pTitle, pMessage, pCallable, pCallback,
				null, false);
	}

	public static <T> void doAsync(final Context pContext,
			final int pTitleResID, final int pMessageResID,
			final Callable<T> pCallable, final Callback<T> pCallback,
			final boolean pCancelable) {
		ActivityUtils.doAsync(pContext, pTitleResID, pMessageResID, pCallable,
				pCallback, null, pCancelable);
	}

	public static <T> void doAsync(final Context pContext,
			final CharSequence pTitle, final CharSequence pMessage,
			final Callable<T> pCallable, final Callback<T> pCallback,
			final boolean pCancelable) {
		ActivityUtils.doAsync(pContext, pTitle, pMessage, pCallable, pCallback,
				null, pCancelable);
	}

	public static <T> void doAsync(final Context pContext,
			final int pTitleResID, final int pMessageResID,
			final Callable<T> pCallable, final Callback<T> pCallback,
			final Callback<Exception> pExceptionCallback) {
		ActivityUtils.doAsync(pContext, pTitleResID, pMessageResID, pCallable,
				pCallback, pExceptionCallback, false);
	}

	public static <T> void doAsync(final Context pContext,
			final CharSequence pTitle, final CharSequence pMessage,
			final Callable<T> pCallable, final Callback<T> pCallback,
			final Callback<Exception> pExceptionCallback) {
		ActivityUtils.doAsync(pContext, pTitle, pMessage, pCallable, pCallback,
				pExceptionCallback, false);
	}

	public static <T> void doAsync(final Context pContext,
			final int pTitleResID, final int pMessageResID,
			final Callable<T> pCallable, final Callback<T> pCallback,
			final Callback<Exception> pExceptionCallback,
			final boolean pCancelable) {
		ActivityUtils.doAsync(pContext, pContext.getString(pTitleResID),
				pContext.getString(pMessageResID), pCallable, pCallback,
				pExceptionCallback, pCancelable);
	}

	public static <T> void doAsync(final Context pContext,
			final CharSequence pTitle, final CharSequence pMessage,
			final Callable<T> pCallable, final Callback<T> pCallback,
			final Callback<Exception> pExceptionCallback,
			final boolean pCancelable) {
		new AsyncTask<Void, Void, T>() {
			private Dialog mPD;
			private Exception mException = null;

			@Override
			public void onPreExecute() {
				if (pMessage != null) {
					// 自定义dialog
					LayoutInflater inflater = LayoutInflater.from(pContext);
					View v = inflater.inflate(R.layout.shv_custom_dialog, null);// 得到加载view
					ImageView spaceshipImage = (ImageView) v
							.findViewById(R.id.img);
					TextView tipTextView = (TextView) v
							.findViewById(R.id.tipTextView);// 提示文字
					Animation hyperspaceJumpAnimation = AnimationUtils
							.loadAnimation(pContext, R.anim.rotate_anim);
					spaceshipImage.startAnimation(hyperspaceJumpAnimation);
					tipTextView.setText(pMessage);// 设置加载信息

					this.mPD = new Dialog(pContext, R.style.loading_dialog);// 创建自定义样式
					this.mPD.getWindow().setWindowAnimations(R.style.dialogWindowAnim);	//自定义dialog弹出样式
					this.mPD.addContentView(v, new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.MATCH_PARENT));

					this.mPD.setCancelable(true);// 设置进度条是否可以按退回键取消
					this.mPD.setCanceledOnTouchOutside(false);// 设置进度条是否可以按退回键取消
					this.mPD.show();
					if (pCancelable) {
						this.mPD.setOnCancelListener(new OnCancelListener() {
							public void onCancel(
									final DialogInterface pDialogInterface) {
								pExceptionCallback
										.onCallback(new CancelledException());
								pDialogInterface.dismiss();
							}
						});
					}
				}
				super.onPreExecute();
			}

			@Override
			public T doInBackground(final Void... params) {
				try {
					return pCallable.call();
				} catch (final Exception e) {
					this.mException = e;
				}
				return null;
			}

			@Override
			public void onPostExecute(final T result) {
				try {
					if (pMessage != null) {
						this.mPD.dismiss();
					}
				} catch (final Exception e) {
					Log.e("Error", e.toString());
				}
				if (this.isCancelled()) {
					this.mException = new CancelledException();
				}
				if (this.mException == null) {
					pCallback.onCallback(result);
				} else {
					if (pExceptionCallback == null) {
						if (this.mException != null)
							Log.e("Error", this.mException.toString());
					} else {
						pExceptionCallback.onCallback(this.mException);
					}
				}
				super.onPostExecute(result);
			}
		}.execute((Void[]) null);

	}

	public static <T> void doProgressAsync(final Context pContext,
			final int pTitleResID, final ProgressCallable<T> pCallable,
			final Callback<T> pCallback) {
		ActivityUtils.doProgressAsync(pContext, pTitleResID, pCallable,
				pCallback, null);
	}

	public static <T> void doProgressAsync(final Context pContext,
			final int pTitleResID, final ProgressCallable<T> pCallable,
			final Callback<T> pCallback,
			final Callback<Exception> pExceptionCallback) {
		new AsyncTask<Void, Integer, T>() {
			private ProgressDialog mPD;
			private Exception mException = null;

			@Override
			public void onPreExecute() {
				this.mPD = new ProgressDialog(pContext);
				this.mPD.setTitle(pTitleResID);
				this.mPD.setIcon(android.R.drawable.ic_menu_save);
				this.mPD.setIndeterminate(false);
				this.mPD.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				this.mPD.show();
				super.onPreExecute();
			}

			@Override
			public T doInBackground(final Void... params) {
				try {
					return pCallable.call(new IProgressListener() {
						public void onProgressChanged(final int pProgress) {
							onProgressUpdate(pProgress);
						}
					});
				} catch (final Exception e) {
					this.mException = e;
				}
				return null;
			}

			@Override
			public void onProgressUpdate(final Integer... values) {
				this.mPD.setProgress(values[0]);
			}

			@Override
			public void onPostExecute(final T result) {
				try {
					this.mPD.dismiss();
				} catch (final Exception e) {
					Log.e("Error", e.getLocalizedMessage());
					/* Nothing. */
				}
				if (this.isCancelled()) {
					this.mException = new CancelledException();
				}
				if (this.mException == null) {
					pCallback.onCallback(result);
				} else {
					if (pExceptionCallback == null) {
						Log.e("Error", this.mException.getLocalizedMessage());
					} else {
						pExceptionCallback.onCallback(this.mException);
					}
				}
				super.onPostExecute(result);
			}
		}.execute((Void[]) null);

	}

	public static <T> void doAsync(final Context pContext,
			final int pTitleResID, final int pMessageResID,
			final AsyncCallable<T> pAsyncCallable, final Callback<T> pCallback,
			final Callback<Exception> pExceptionCallback) {
		final ProgressDialog pd = ProgressDialog.show(pContext,
				pContext.getString(pTitleResID),
				pContext.getString(pMessageResID));
		pAsyncCallable.call(new Callback<T>() {

			public void onCallback(final T result) {
				try {
					pd.dismiss();
				} catch (final Exception e) {
					Log.e("Error", e.getLocalizedMessage());
					/* Nothing. */
				}
				pCallback.onCallback(result);
			}
		}, pExceptionCallback);

	}

	public static class CancelledException extends Exception {
		private static final long serialVersionUID = -78123211381435595L;
	}
}
