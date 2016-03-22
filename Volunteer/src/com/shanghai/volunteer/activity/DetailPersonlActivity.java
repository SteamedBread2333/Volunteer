package com.shanghai.volunteer.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Callable;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.activity.view.RoundedImageView;
import com.shanghai.volunteer.application.SHVolunteerApplication;
import com.shanghai.volunteer.camera.CameraCallback;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class DetailPersonlActivity extends BaseActivity {

	private TextView title,nickName, logout, mobile, 
			IdNo;
	private RoundedImageView personlUserImageIV;
	private LinearLayout changeUserImage;
	private PopupWindow mPopupWindow;
	public CameraCallback cameraCallback = new CameraCallback(this);
	public Boolean isUploadPhoto = false;
	byte[] b = null;
	private Intent intent;
	// 操作参数
	String filePath = null;
	private DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailpersonl);
		options = new DisplayImageOptions.Builder() // 设置图片下载期间显示的图片
				.showImageOnLoading(R.drawable.white)
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build();
		findView();
	}

	private void findView() {
		// TODO Auto-generated method stub
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.edit).setOnClickListener(this);
		personlUserImageIV = (RoundedImageView) findViewById(R.id.personlUserImageIV);
		ImageLoader.getInstance().displayImage(Constants.mAccount.getUserImg(),
				personlUserImageIV , options);
		changeUserImage = (LinearLayout) findViewById(R.id.changeUserImage);
		title = (TextView) findViewById(R.id.title);
		nickName = (TextView) findViewById(R.id.nickName);
		mobile = (TextView) findViewById(R.id.mobile);
		IdNo = (TextView) findViewById(R.id.IdNo);
		logout = (TextView) findViewById(R.id.logout);
		title.setText("详细信息");
		logout.setOnClickListener(this);
		changeUserImage.setOnClickListener(this);
		nickName.setText("昵称：" + Constants.mAccount.getNickName());
		mobile.setText("手机号：" + Constants.mAccount.getUserPhone());
		IdNo.setText("证件号：" + Constants.mAccount.getIdNo());
	}

	/*
	 * 创建PopupWindow
	 */
	private void initPopuptWindow() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(
				R.layout.alert_dialog_changeuserimage, null);
		((Button) popupWindow.findViewById(R.id.selectfromimage))
				.setOnClickListener(this);
		((Button) popupWindow.findViewById(R.id.makeimage))
				.setOnClickListener(this);
		((Button) popupWindow.findViewById(R.id.cancel))
				.setOnClickListener(this);
		// 创建一个PopupWindow
		// 参数1：contentView 指定PopupWindow的内容
		// 参数2：width 指定PopupWindow的width
		// 参数3：height 指定PopupWindow的height
		mPopupWindow = new PopupWindow(popupWindow,
				WindowManager.LayoutParams.MATCH_PARENT, Constants.dip2px(this,
						300));
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.logout:
			SHVolunteerApplication.getInstance().clearAccount();
			Constants.mAccount = null;
			sendBroadcast(new Intent(PersonlCenterActivity.MY_PROFILE_ACTION1));
			finish();
			break;
		case R.id.changeUserImage:
			initPopuptWindow();
			mPopupWindow.showAtLocation(view, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0);
			InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm1.hideSoftInputFromWindow(view.getWindowToken(), 0);
			break;
		case R.id.selectfromimage:
			cameraCallback.tuku2Pictrue();
			break;
		case R.id.makeimage:
			isUploadPhoto = true;
			cameraCallback.camera2Pic();
			break;
		case R.id.cancel:
			mPopupWindow.dismiss();
			break;
		case R.id.edit:
			intent = new Intent(this, ChangePwdActivity.class);
			startActivity(intent);
			break;
		}
	}

	public void uploadImage(byte[] b) {
		final String infoString = Base64.encodeToString(b, Base64.DEFAULT);
		this.doAsync("数据上传中，请稍侯...", new Callable<String>() {

			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().updataUserIcon(
							Constants.mAccount.getUserID(), infoString);
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<String>() {

			@Override
			public void onCallback(String pCallbackValue) {
				// TODO Auto-generated method stub
				if (pCallbackValue != null) {
					//ImageLoader.getInstance().getDiskCache().
					MemoryCacheUtils.removeFromCache(pCallbackValue, ImageLoader.getInstance().getMemoryCache());
					DiskCacheUtils.removeFromCache(pCallbackValue, ImageLoader.getInstance().getDiskCache());
					ImageLoader.getInstance().displayImage(pCallbackValue,
							personlUserImageIV);
					Constants.mAccount.setUserImg(pCallbackValue);
					showToast("头像上传成功");
				} else {
					showToast("头像上传失败");
				}
			}
		});
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static byte[] getLocalImage2ByteArray(String filePath) {
		byte[] data = null;
		File file = null;
		FileInputStream fis = null;
		try {
			file = new File(filePath);
			if (file != null) {
				fis = new FileInputStream(file);
				data = new byte[fis.available()];
				fis.read(data);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return data;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_OK) {

			cameraCallback
					.setCallBackParameter(requestCode, resultCode, intent);
			filePath = cameraCallback.getFilePath();
			cameraCallback.copySmallImage(filePath);
			if (requestCode == CameraCallback.SELECT_PICTURE) { // 相册中选择头像上传
				b = getLocalImage2ByteArray(filePath);
				mPopupWindow.dismiss();
				uploadImage(b);
				// showToast("相册中选择头像上传");
			} else if (requestCode == CameraCallback.CAMERA_RESULT
					&& isUploadPhoto) { // 头像拍照上传
				b = getLocalImage2ByteArray(filePath);
				mPopupWindow.dismiss();
				uploadImage(b);
			}
			isUploadPhoto = false;
		}
	}
}
