package com.shanghai.volunteer.activity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.adapter.BannerAdapter;
import com.shanghai.volunteer.bean.MainPage;
import com.shanghai.volunteer.bean.News;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class MainActivity extends BaseActivity {

	private long mExitTime;
	private Timer heartTimer = null;
	private HeartTimerTask heartTimerTask = null;
	private ViewPager bannerViewPager;
	private LinearLayout bannerViewPagerControl;
	private TextView news, liantong, zhoubian, more, NewsTitle1, NewsTitle2,
			NewsTitle3, NewsTitle4;
	private Intent intent;
	private ImageView goto_active, goto_volunteer, goto_aboutus, goto_callme,
			personl, search;
	ArrayList<TextView> TVList = new ArrayList<TextView>();

	private MainPage mainPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		onWindowFocusChanged(true);
		findView();
		getCache();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		Constants.ScreenWidth = dm.widthPixels;
		Constants.ScreenHeight = dm.heightPixels;
		Constants.ScreenDensity = dm.density;
	}

	private void getCache() {
		mainPage = MainPage.getMainData(this);
		if (mainPage != null) {
			for (int i = 0; i < mainPage.getActiveArray().size(); i++) {
				TVList.get(i).setText(
						mainPage.getActiveArray().get(i).getTitle());
			}
			BannerAdapter bannerAdapter = new BannerAdapter(MainActivity.this);
			bannerAdapter.setBanners(mainPage.getNewsArray());
			bannerViewPager.setAdapter(bannerAdapter);
			bannerViewPager
					.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

						@Override
						public void onPageSelected(int arg0) {
							setBannerPointer(bannerViewPagerControl, arg0);
						}

						@Override
						public void onPageScrolled(int arg0, float arg1,
								int arg2) {
						}

						@Override
						public void onPageScrollStateChanged(int arg0) {

						}
					});

			addBannerPointer(bannerViewPagerControl, mainPage.getNewsArray());
			setBannerPointer(bannerViewPagerControl, 0);
		}
		if (heartTimer == null) {
			heartTimer = new Timer(true);
		}
		if (heartTimerTask == null) {
			heartTimerTask = new HeartTimerTask();
		}
		heartTimer.schedule(heartTimerTask, 5000, 5000);
		getData();
	}

	private void getData() {
		// 读取缓存
		String titleT = null;
		if (mainPage == null)
			titleT = "";
		// TODO Auto-generated method stub
		this.doAsync(titleT, new Callable<MainPage>() {

			@Override
			public MainPage call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().GetHomePage();
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<MainPage>() {

			@Override
			public void onCallback(MainPage pCallbackValue) {
				// TODO Auto-generated method stub
				if (pCallbackValue != null) {
					mainPage = pCallbackValue;
					MainPage.saveMainData(mainPage, MainActivity.this);
					for (int i = 0; i < pCallbackValue.getActiveArray().size(); i++) {
						TVList.get(i).setText(
								pCallbackValue.getActiveArray().get(i)
										.getTitle());
					}
					BannerAdapter bannerAdapter = new BannerAdapter(
							MainActivity.this);

					bannerAdapter.setBanners(mainPage.getNewsArray());
					bannerViewPager.setAdapter(bannerAdapter);
					bannerViewPager
							.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

								@Override
								public void onPageSelected(int arg0) {
									setBannerPointer(bannerViewPagerControl,
											arg0);
								}

								@Override
								public void onPageScrolled(int arg0,
										float arg1, int arg2) {
								}

								@Override
								public void onPageScrollStateChanged(int arg0) {

								}
							});

					addBannerPointer(bannerViewPagerControl,
							mainPage.getNewsArray());
					setBannerPointer(bannerViewPagerControl, 0);
				}
			}
		}, new Callback<Exception>() {

			@Override
			public void onCallback(Exception pCallbackValue) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void findView() {

		// banner
		bannerViewPager = new ViewPager(this);

		FrameLayout fl = (FrameLayout) findViewById(R.id.homeBannerLayout);
		fl.addView(bannerViewPager, 0, new FrameLayout.LayoutParams(
				Constants.ScreenWidth, Constants.ScreenWidth / 2));

		bannerViewPagerControl = (LinearLayout) findViewById(R.id.bannerViewPagerControl);

		// TODO Auto-generated method stub
		news = (TextView) findViewById(R.id.news);
		liantong = (TextView) findViewById(R.id.liantong);
		zhoubian = (TextView) findViewById(R.id.zhoubian);
		more = (TextView) findViewById(R.id.more);
		goto_active = (ImageView) findViewById(R.id.goto_active);
		goto_volunteer = (ImageView) findViewById(R.id.goto_volunteer);
		goto_aboutus = (ImageView) findViewById(R.id.goto_aboutus);
		goto_callme = (ImageView) findViewById(R.id.goto_callme);
		personl = (ImageView) findViewById(R.id.personl);
		search = (ImageView) findViewById(R.id.search);
		NewsTitle1 = (TextView) findViewById(R.id.NewsTitle1);
		NewsTitle2 = (TextView) findViewById(R.id.NewsTitle2);
		NewsTitle3 = (TextView) findViewById(R.id.NewsTitle3);
		NewsTitle4 = (TextView) findViewById(R.id.NewsTitle4);
		NewsTitle1.setOnClickListener(this);
		NewsTitle2.setOnClickListener(this);
		NewsTitle3.setOnClickListener(this);
		NewsTitle4.setOnClickListener(this);
		TVList.add(NewsTitle1);
		TVList.add(NewsTitle2);
		TVList.add(NewsTitle3);
		TVList.add(NewsTitle4);
		news.setOnClickListener(this);
		liantong.setOnClickListener(this);
		zhoubian.setOnClickListener(this);
		more.setOnClickListener(this);
		goto_active.setOnClickListener(this);
		goto_volunteer.setOnClickListener(this);
		goto_aboutus.setOnClickListener(this);
		goto_callme.setOnClickListener(this);
		personl.setOnClickListener(this);
		search.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		super.onItemClick(adapterView, view, arg2, arg3);
		intent = new Intent(this, DetailActiverActivity.class);
		intent.putExtra("activeId", mainPage.getActiveArray().get(arg2).getID());
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.news:
			intent = new Intent(this, NewsListActivity.class);
			startActivity(intent);
			break;
		case R.id.liantong:
			if (Constants.mAccount == null) {
				intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				intent = new Intent(this, PersonlCenterActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.zhoubian:
			intent = new Intent(this, ZhouBianActivity.class);
			startActivity(intent);
			break;
		case R.id.more:
			intent = new Intent(this, MoreAvtivity.class);
			startActivity(intent);
			break;
		case R.id.goto_active:
			break;
		case R.id.goto_volunteer:
			intent = new Intent(this, ActiveListActivity.class);
			startActivity(intent);
			break;
		case R.id.goto_aboutus:
			intent = new Intent(this, AboutUsActivity.class);
			startActivity(intent);
			break;
		case R.id.goto_callme:
			intent = new Intent(this, CallMeActivity.class);
			startActivity(intent);
			break;
		case R.id.personl:
			if (Constants.mAccount == null) {
				intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				intent = new Intent(this, PersonlCenterActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.search:
			intent = new Intent(this, SearchActiveActivity.class);
			startActivity(intent);
			break;
		case R.id.NewsTitle1: {
			if (mainPage.getActiveArray().size() > 0) {
				intent = new Intent(this, DetailActiverActivity.class);
				intent.putExtra("activeId", mainPage.getActiveArray().get(0)
						.getID());
				startActivity(intent);
			}
			break;
		}
		case R.id.NewsTitle2: {
			if (mainPage.getActiveArray().size() > 1) {
				intent = new Intent(this, DetailActiverActivity.class);
				intent.putExtra("activeId", mainPage.getActiveArray().get(1)
						.getID());
				startActivity(intent);
			}
			break;
		}
		case R.id.NewsTitle3: {
			if (mainPage.getActiveArray().size() > 2) {
				intent = new Intent(this, DetailActiverActivity.class);
				intent.putExtra("activeId", mainPage.getActiveArray().get(2)
						.getID());
				startActivity(intent);
			}
			break;
		}
		case R.id.NewsTitle4: {
			if (mainPage.getActiveArray().size() > 3) {
				intent = new Intent(this, DetailActiverActivity.class);
				intent.putExtra("activeId", mainPage.getActiveArray().get(3)
						.getID());
				startActivity(intent);
			}
			break;
		}
		default:
			break;
		}
	}

	/**
	 * 添加指引点
	 * */
	private void addBannerPointer(LinearLayout group, ArrayList<News> newsArray) {
		group.removeAllViews();
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(8, 0, 8, 30);
		ImageView pointIv = null;
		for (int i = 0; i < newsArray.size(); i++) {
			pointIv = new ImageView(this);
			pointIv.setImageResource(R.drawable.dot);
			pointIv.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

				}
			});

			group.addView(pointIv, lp);
		}
	}

	/**
	 * 设置当前指引点
	 * */
	private void setBannerPointer(LinearLayout group, int index) {
		for (int i = 0; i < group.getChildCount(); i++) {
			if (i == index) {
				((ImageView) group.getChildAt(i))
						.setImageResource(R.drawable.dot_l);
				AnimatorSet set = new AnimatorSet();
				set.playTogether(
						ObjectAnimator.ofFloat(group.getChildAt(i), "scaleX",
								0.8f, 1.2f, 1.0f).setDuration(500),
						ObjectAnimator.ofFloat(group.getChildAt(i), "scaleY",
								0.8f, 1.2f, 1.0f).setDuration(500));
				set.start();
			} else
				((ImageView) group.getChildAt(i))
						.setImageResource(R.drawable.dot);

		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if ((System.currentTimeMillis() - mExitTime) > 2000) {

				Object mHelperUtils;

				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();

				mExitTime = System.currentTimeMillis();

			} else {

				finish();

			}

			return true;

		}

		return super.onKeyDown(keyCode, event);

	}

	class HeartTimerTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mainPage.getNewsArray() != null
							&& mainPage.getNewsArray().size() > 0) {
						if (bannerViewPager.getCurrentItem() < mainPage
								.getNewsArray().size()-1)
							bannerViewPager.setCurrentItem(
									bannerViewPager.getCurrentItem() + 1, true);
						else
							bannerViewPager.setCurrentItem(0, true);
						setBannerPointer(bannerViewPagerControl,
								bannerViewPager.getCurrentItem());
					}
				}
			});
		}

	}
}
