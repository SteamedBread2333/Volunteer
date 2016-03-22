package com.shanghai.volunteer.activity;

import java.io.File;
import java.io.OutputStream;
import java.util.concurrent.Callable;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.ShareModel;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.bean.Display;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;
import com.shanghai.volunteer.other.parallax.ParallaxScrollView;
import com.umeng.socialize.media.UMImage;

public class DetailDisplayActivity extends BaseActivity {

	private ImageView back, displayIM, share, largeImage;
	private TextView title, displayTitleTV, displayAuthorTV,
			displayCreatTimeTV, displayDetailTV;
	private ImageButton nice, command;
	private ParallaxScrollView displayPSC;
	private PopupWindow mPopupWindow;
	private Bitmap bigSmallBitmap;
	ShareModel mShare = new ShareModel(this);
	private Intent intent;
	Display data = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detaildisplay);
		findView();
	}

	/*
	 * 创建PopupWindow
	 */
	private void initPopuptWindow() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(
				R.layout.alert_dialog_saveimage, null);
		((Button) popupWindow.findViewById(R.id.saveimage))
				.setOnClickListener(this);
		((Button) popupWindow.findViewById(R.id.cancel))
				.setOnClickListener(this);
		// 创建一个PopupWindow
		// 参数1：contentView 指定PopupWindow的内容
		// 参数2：width 指定PopupWindow的width
		// 参数3：height 指定PopupWindow的height
		mPopupWindow = new PopupWindow(popupWindow,
				WindowManager.LayoutParams.MATCH_PARENT, Constants.dip2px(this,
						200));
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

	}

	private void getData() {
		// TODO Auto-generated method stub
		this.doAsync("数据加载中，请稍候...", new Callable<Display>() {

			@Override
			public Display call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl()
							.getDetailDisplay(getIntent().getStringExtra("mId"));
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<Display>() {

			@Override
			public void onCallback(Display pCallbackValue) {
				// TODO Auto-generated method stub
				if (pCallbackValue != null) {
					data = pCallbackValue;
					ImageLoader.getInstance().displayImage(
							pCallbackValue.getImageUrl(), displayIM, null,
							new ImageLoadingListener() {

								@Override
								public void onLoadingStarted(String arg0,
										View arg1) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onLoadingFailed(String arg0,
										View arg1, FailReason arg2) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onLoadingComplete(String arg0,
										View arg1, Bitmap arg2) {
									// TODO Auto-generated method stub
									bigSmallBitmap = arg2;
								}

								@Override
								public void onLoadingCancelled(String arg0,
										View arg1) {
									// TODO Auto-generated method stub

								}
							});

					displayTitleTV.setText(pCallbackValue.getTitle());
					displayAuthorTV.setText("来自：" + pCallbackValue.getUName());
					String a[] = pCallbackValue.getCreatTime().split("T");
					displayCreatTimeTV.setText("创建于：" + a[0]);
					displayDetailTV.setText(pCallbackValue.getDetails());
					// nice.setText(pCallbackValue.getPraiseNum());
					// command.setText(pCallbackValue.getReviewCount());
				}
			}
		});
	}

	private void findView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.back);
		displayIM = (ImageView) findViewById(R.id.displayIM);
		title = (TextView) findViewById(R.id.title);
		share = (ImageView) findViewById(R.id.share);
		displayTitleTV = (TextView) findViewById(R.id.displayTitleTV);
		displayAuthorTV = (TextView) findViewById(R.id.displayAuthorTV);
		displayCreatTimeTV = (TextView) findViewById(R.id.displayCreatTimeTV);
		displayDetailTV = (TextView) findViewById(R.id.displayDetailTV);
		displayPSC = (ParallaxScrollView) findViewById(R.id.displayPSC);
		nice = (ImageButton) findViewById(R.id.nice);
		largeImage = (ImageView) findViewById(R.id.largeImage);
		// bad = (TextView) findViewById(R.id.bad);
		command = (ImageButton) findViewById(R.id.command);
		displayIM.setDrawingCacheEnabled(true);
		displayIM.setOnClickListener(this);
		displayIM.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View view) {
				// TODO Auto-generated method stub
				if (data != null) {
					initPopuptWindow();
					mPopupWindow.showAtLocation(view, Gravity.BOTTOM
							| Gravity.CENTER_HORIZONTAL, 0, 0);
					InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm1.hideSoftInputFromWindow(view.getWindowToken(), 0);
				}
				return false;
			}
		});
		largeImage.setOnClickListener(this);

		back.setOnClickListener(this);
		share.setOnClickListener(this);
		nice.setOnClickListener(this);
		// bad.setOnClickListener(this);
		command.setOnClickListener(this);
		title.setText("风采展示详情");
		getData();
		displayPSC.setImageViewToParallax(displayIM);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.share:
			String url = "http://www.volunteer.sh.cn/website/DownAPP.aspx";
			Bitmap bp = displayIM.getDrawingCache();
			UMImage ui = new UMImage(this, bp);
			mShare.SetController(data.getDetails(), data.getTitle(), url, ui);
			mPopupWindow = mShare.initPopuptWindow();
			mPopupWindow.showAtLocation(view, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0);
			break;
		case R.id.nice:
			biaoTai(1);
			break;
		/*
		 * case R.id.bad: biaoTai(0); break;
		 */
		case R.id.command:
			intent = new Intent(this, CommandActivity.class);
			intent.putExtra("mId", getIntent().getStringExtra("mId"));
			startActivity(intent);
			break;
		case R.id.displayIM:
			if (data != null) {
				ImageLoader.getInstance().displayImage(data.getImageUrl(),
						largeImage);
				largeImage.setDrawingCacheEnabled(true);
				largeImage.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.largeImage:
			largeImage.setVisibility(View.GONE);
			break;
		case R.id.saveimage:
			saveImage(bigSmallBitmap);

			break;
		case R.id.cancel:
			mPopupWindow.dismiss();
			break;
		}
	}

	private void saveImage(Bitmap btimapT) {
		// TODO Auto-generated method stub
		Bitmap mBmpForSave = btimapT;
		mPopupWindow.dismiss();
		// ContentValues values = new ContentValues(8);
		// String newname = DateFormat.format("yyyyMMddmmss",
		// System.currentTimeMillis()).toString();
		try {
			// 写入数据
			ContentResolver cr = this.getContentResolver();
			String url = MediaStore.Images.Media.insertImage(cr, mBmpForSave, "title",
					"desc");
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
					Uri.parse(url)));
			showToast("保存成功");
			return;
		} catch (Exception e) {
			showToast("保存失败");
		}

	}

	private void biaoTai(final int isPraise) {
		// TODO Auto-generated method stub
		this.doAsync(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().PraiseMein(getIntent()
							.getStringExtra("mId"), Constants.mAccount
							.getUserID(), isPraise);
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
				if (pCallbackValue == 0) {
					showToast("表态失败");
				} else if (pCallbackValue == 1) {
					showToast("表态成功");
				} else if (pCallbackValue == 2) {
					showToast("已表态");
				}
			}
		});
	}
}
