package com.shanghai.volunteer.activity;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.bean.Active;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class ZhouBianActivity extends BaseActivity {

	// ��λ���
	LocationClient mLocClient;
	private ArrayList<Active> activityArray;
	MyLocationData locData;
	public MyLocationListenner myListener = new MyLocationListenner();
	MapView mMapView;
	BaiduMap mBaiduMap;
	boolean isFirstLoc = true;// �Ƿ��״ζ�λ
	String Traffic = "�ܱ�";

	private int pageIndex = 0;
	private int pageSize = 10;
	Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_zhoubian);
		// title
		((ImageView) findViewById(R.id.back)).setOnClickListener(this);
		((TextView) findViewById(R.id.title)).setText(Traffic);

		// ��ͼ��ʼ��
		mMapView = (MapView) findViewById(R.id.bmapView);

		// �������ſؼ�
		int childCount = mMapView.getChildCount();
		View zoom = null;
		for (int i = 0; i < childCount; i++) {
			View child = mMapView.getChildAt(i);
			if (child instanceof ZoomControls) {
				zoom = child;
				break;
			}
		}
		zoom.setVisibility(View.GONE);
		mBaiduMap = mMapView.getMap();
		// ������λͼ��
		mBaiduMap.setMyLocationEnabled(true);
		// ��λ��ʼ��
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		// �����ͼ״̬
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(13.0f);
		mBaiduMap.setMapStatus(msu);

		// mark ����¼�
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(final Marker marker) {

				final View popview = LayoutInflater.from(ZhouBianActivity.this)
						.inflate(R.layout.popup_view, null);
				final TextView titleTV = (TextView) popview
						.findViewById(R.id.titleTV);
				for (Active active : activityArray) {
					if (active.getLatitude() == marker.getPosition().latitude
							&& active.getLongitude() == marker.getPosition().longitude) {
						titleTV.setText(active.getTitle());
						titleTV.setTag(active.getID());
						break;
					}
				}

				InfoWindow mInfoWindow = new InfoWindow(popview, marker
						.getPosition(), new OnInfoWindowClickListener() {
					public void onInfoWindowClick() {
						// ��תҳ��
						mBaiduMap.hideInfoWindow();
						intent = new Intent(ZhouBianActivity.this,
								DetailActiverActivity.class);
						intent.putExtra("activeId", titleTV.getTag().toString());
						startActivity(intent);
					}
				});
				// new infow
				mBaiduMap.showInfoWindow(mInfoWindow);
				return false;
			}
		});

	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		}
	}

	public void initOverlay() {
		// add marker overlay
		this.doAsync("���ݼ����У����Ժ�...", new Callable<ArrayList<Active>>() {

			@Override
			public ArrayList<Active> call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().getItemListByCoordinates(
							pageIndex, pageSize,
							String.valueOf(locData.longitude),
							String.valueOf(locData.latitude));
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<ArrayList<Active>>() {

			@Override
			public void onCallback(ArrayList<Active> pCallbackValue) {
				// TODO Auto-generated method stub
				if (pCallbackValue != null) {
					activityArray = pCallbackValue;
					for (Active active : pCallbackValue) {
						// ����Maker�����
						LatLng point = new LatLng(active.getLatitude(), active
								.getLongitude());
						// ����Markerͼ��
						BitmapDescriptor bitmap = BitmapDescriptorFactory
								.fromResource(R.drawable.icon_openmap_mark);
						// ����MarkerOption�������ڵ�ͼ�����Marker
						OverlayOptions option = new MarkerOptions().position(
								point).icon(bitmap);
						// �ڵ�ͼ�����Marker������ʾ
						mBaiduMap.addOverlay(option);
					}
				}else{
					showToast("�ܱ��޻��");
				}
			}
		});

	}

	/**
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view ���ٺ��ڴ����½��յ�λ��
			if (location == null || mMapView == null)
				return;
			locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
				initOverlay();
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		mLocClient.start();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// �˳�ʱ���ٶ�λ
		mLocClient.stop();
		// �رն�λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
}
