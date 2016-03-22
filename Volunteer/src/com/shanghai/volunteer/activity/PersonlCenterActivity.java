package com.shanghai.volunteer.activity;

import java.util.concurrent.Callable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.activity.view.RoundedImageView;
import com.shanghai.volunteer.application.SHVolunteerApplication;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class PersonlCenterActivity extends BaseActivity {

	

	private ImageView signin, addone;
	private TextView person_detail, person_photo, person_activity,
			signin_record, person_unicom, personlNameTV;
	private Intent intent;
	LocationClient mLocClient;
	MyLocationData locData = null;
	private RoundedImageView personlUserImageIV;
	public MyLocationListenner myListener = new MyLocationListenner();
	private boolean num = true;
	public static final String MY_PROFILE_ACTION1 = "my_profile_action1";
	private MyBroadcastReceiver myProfileReceiver = new MyBroadcastReceiver();
	private DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personlcenter);
		registerReceiver(myProfileReceiver,
				new IntentFilter(MY_PROFILE_ACTION1));
		options = new DisplayImageOptions.Builder() // 设置图片下载期间显示的图片
				//.showImageOnLoading(R.drawable.white)
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build();
		findView();
		getData();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ImageLoader.getInstance().displayImage(Constants.mAccount.getUserImg(),
				personlUserImageIV, options);
		//ImageLoader.getInstance().displayImage(uri, imageAware);
	}
	private void getData() {
		// TODO Auto-generated method stub
		this.doAsync(null, new Callable<String>() {

			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl()
							.getUserIcon(Constants.mAccount.getUserID());
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
					if(!pCallbackValue.equals(Constants.mAccount.getUserImg())){
						Constants.mAccount.setUserImg(pCallbackValue);
						SHVolunteerApplication.getInstance().saveAccount(
								Constants.mAccount);
						ImageLoader.getInstance().displayImage(pCallbackValue,
								personlUserImageIV, options);
					}
				}
			}
		});
	}

	private void findView() {
		// TODO Auto-generated method stub
		signin = (ImageView) findViewById(R.id.signin);
		addone = (ImageView) findViewById(R.id.addone);
		personlUserImageIV = (RoundedImageView) findViewById(R.id.personlUserImageIV);
		//ImageLoader.getInstance().displayImage(Constants.mAccount.getUserImg(),
		//		personlUserImageIV, options);
		person_detail = (TextView) findViewById(R.id.person_detail);
		person_photo = (TextView) findViewById(R.id.person_photo);
		person_activity = (TextView) findViewById(R.id.person_activity);
		person_unicom = (TextView) findViewById(R.id.person_unicom);
		signin_record = (TextView) findViewById(R.id.signin_record);
		personlNameTV = (TextView) findViewById(R.id.personlNameTV);
		personlNameTV.setText(Constants.mAccount.getNickName());
		findViewById(R.id.back).setOnClickListener(this);
		signin.setOnClickListener(this);
		person_detail.setOnClickListener(this);
		person_photo.setOnClickListener(this);
		person_activity.setOnClickListener(this);
		signin_record.setOnClickListener(this);
		person_unicom.setOnClickListener(this);
		getLoc();
	}

	private void playAnimatorSet() {
		// TODO Auto-generated method stub
		addone.setVisibility(View.VISIBLE);
		AnimatorSet as = new AnimatorSet();
		as.playTogether(
				ObjectAnimator.ofFloat(addone, "translationY", 0f, -50.f),
				ObjectAnimator.ofFloat(addone, "alpha", 1.0f, 0.f));
		as.setDuration(1500);
		as.start();
	}

	private void getLoc() {
		// TODO Auto-generated method stub
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null)
				return;
			locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			if (location.getLatitude() != 4.9E-324) {

			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	private void signIn(final double Longitude, final double Latitude) {
		// TODO Auto-generated method stub
		this.doAsync("签到中，请稍候...", new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub

				try {
					return new SHVolunteerApiImpl().Signin(
							Constants.mAccount.getUserID(),
							Constants.mAccount.getNickName(), Longitude,
							Latitude);
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
					showToast("签到成功");
					playAnimatorSet();
				} else if (pCallbackValue == 0) {
					showToast("签到失败");
				} else if (pCallbackValue == 2) {
					showToast("已签到");
				}
			}
		});
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.signin:
			if (locData != null) {
				signIn(locData.longitude, locData.latitude);
			} else {
				showToast("正在定位...");
			}
			break;
		case R.id.person_detail:
			intent = new Intent(this, DetailPersonlActivity.class);
			startActivity(intent);
			break;
		case R.id.person_photo:
			intent = new Intent(this, MyDisplayListActivity.class);
			startActivity(intent);
			break;
		case R.id.person_activity:
			intent = new Intent(this, MyActiveListActivity.class);
			startActivity(intent);
			break;
		case R.id.signin_record:
			intent = new Intent(this, SigninHistoryActivity.class);
			startActivity(intent);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.person_unicom:
			intent = new Intent(this, WebListActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		if (mLocClient != null) {
			mLocClient.stop();
		}
		unregisterReceiver(myProfileReceiver);
		super.onDestroy();
	}

	class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			if (arg1.getAction().equals(MY_PROFILE_ACTION1)) {
				finish();
			}
		}

	}

}
