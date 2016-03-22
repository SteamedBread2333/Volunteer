package com.shanghai.volunteer.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Callable;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.camera.CameraCallback;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class GetSuishoupaiActivity extends BaseActivity {

	private TextView title;
	private ImageView getphoto_IM, back, upload;
	private EditText getphoto_titleET, getphoto_detailET;
	private PopupWindow mPopupWindow;
	public CameraCallback cameraCallback = new CameraCallback(this);
	public Boolean isUploadPhoto = false;
	byte[] b = null;
	// 操作参数
	String filePath = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getsuishoupai);
		findView();

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

	private void findView() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.title);
		getphoto_IM = (ImageView) findViewById(R.id.getphoto_IM);
		back = (ImageView) findViewById(R.id.back);
		getphoto_titleET = (EditText) findViewById(R.id.getphoto_titleET);
		getphoto_detailET = (EditText) findViewById(R.id.getphoto_detailET);
		upload = (ImageView) findViewById(R.id.upload);
		title.setText("我的随手拍");
		back.setOnClickListener(this);
		getphoto_IM.setOnClickListener(this);
		upload.setOnClickListener(this);
		findViewById(R.id.hidekeybo).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.getphoto_IM:
			initPopuptWindow();
			mPopupWindow.showAtLocation(view, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0);
			InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm1.hideSoftInputFromWindow(view.getWindowToken(), 0);
			break;
		case R.id.upload:
			if (b != null) {
				uploadImage(b);
				finish();
			}
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
		case R.id.hidekeybo:
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public void uploadImage(byte[] b) {
		final String infoString = Base64.encodeToString(b, Base64.DEFAULT);
		this.doAsync("数据上传中，请稍侯...", new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().addMein(Constants.mAccount
							.getUserID(), Constants.mAccount.getNickName(),
							getphoto_titleET.getText().toString(),
							getphoto_detailET.getText().toString(), infoString);
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 0;
			}
		}, new Callback<Integer>() {

			@Override
			public void onCallback(Integer pCallbackValue) {
				// TODO Auto-generated method stub
				if (pCallbackValue == 1) {
					showToast("发布随手拍成功，待审核！");
				} else {
					showToast("发布失败");
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
			if(mPopupWindow != null)
				mPopupWindow.dismiss();
			cameraCallback
					.setCallBackParameter(requestCode, resultCode, intent);
			filePath = cameraCallback.getFilePath();
			cameraCallback.copySmallImage(filePath);
			if (requestCode == CameraCallback.SELECT_PICTURE) { // 相册中选择头像上传
				b = getLocalImage2ByteArray(filePath);
				ImageLoader.getInstance().displayImage("file://" + filePath,
						getphoto_IM);
				// showToast("相册中选择头像上传");
			} else if (requestCode == CameraCallback.CAMERA_RESULT
					&& isUploadPhoto) { // 头像拍照上传
				b = getLocalImage2ByteArray(filePath);
				ImageLoader.getInstance().displayImage("file://" + filePath,
						getphoto_IM);
			}
			isUploadPhoto = false;
		}
	}
}
