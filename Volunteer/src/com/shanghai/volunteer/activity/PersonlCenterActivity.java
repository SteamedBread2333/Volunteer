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
	private DisplayImageOptions options; // DisplayImageOptions����������ͼƬ��ʾ����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personlcenter);
		registerReceiver(myProfileReceiver,
				new IntentFilter(MY_PROFILE_ACTION1));
		options = new DisplayImageOptions.Builder() // ����ͼƬ�����ڼ���ʾ��ͼƬ
				//.showImageOnLoading(R.drawable.white)
				.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisk(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
				// .displayer(new RoundedBitmapDisplayer(20)) // ���ó�Բ��ͼƬ
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
		option.setOpenGps(true);// ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	/**
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view ���ٺ��ڴ����½��յ�λ��
			if (location == null)
				return;
			locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
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
		this.doAsync("ǩ���У����Ժ�...", new Callable<Integer>() {

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
					showToast("ǩ���ɹ�");
					playAnimatorSet();
				} else if (pCallbackValue == 0) {
					showToast("ǩ��ʧ��");
				} else if (pCallbackValue == 2) {
					showToast("��ǩ��");
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
				showToast("���ڶ�λ...");
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
		// �˳�ʱ���ٶ�λ
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
