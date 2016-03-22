package com.shanghai.volunteer.activity;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.adapter.DisplayAdapter;
import com.shanghai.volunteer.bean.Display;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class DisplayListActivity extends BaseActivity {

	private GridView pull_refresh_displaylist;
	static final int MENU_SET_MODE = 0;
	private DisplayAdapter mAdapter;
	private int pageIndex = -1;
	private int pageSize = 10;
	private int orderby = 1;
	ArrayList<Display> data = new ArrayList<Display>();
	private TextView byhot_on, bytime_on, bytime_off, byhot_off, title;
	private ImageView back, personl,noPhotoDisplayIV;
	private Intent intent;
	LinearLayout by_Linear;
	private ProgressBar loadingPBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_displaygrid);
		title = (TextView) findViewById(R.id.title);
		title.setText("风采展示");
		by_Linear = (LinearLayout) findViewById(R.id.by_Linear);
		back = (ImageView) findViewById(R.id.back);
		personl = (ImageView) findViewById(R.id.personl);
		noPhotoDisplayIV = (ImageView) findViewById(R.id.noPhotoDisplayIV);
		byhot_on = (TextView) findViewById(R.id.byhot_on);
		bytime_on = (TextView) findViewById(R.id.bytime_on);
		bytime_off = (TextView) findViewById(R.id.bytime_off);
		byhot_off = (TextView) findViewById(R.id.byhot_off);
		personl.setOnClickListener(this);
		back.setOnClickListener(this);
		bytime_off.setOnClickListener(this);
		byhot_off.setOnClickListener(this);
		pull_refresh_displaylist = (GridView) findViewById(R.id.pull_refresh_displaylist);
		// add footer
		loadingPBar = (ProgressBar)findViewById(R.id.disPlayFooterLoadingPbar);
		mAdapter = new DisplayAdapter(this);
		mAdapter.setList(new ArrayList<Display>());
		pull_refresh_displaylist.setAdapter(mAdapter);
		pull_refresh_displaylist.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

				switch (scrollState) {
				// 当不滚动时
				case OnScrollListener.SCROLL_STATE_IDLE:
					
					// 判断滚动到底部
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
						getData();
					}
			
				}
			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
			}
		});
		getData();
	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.bytime_off:
			bytime_off.setVisibility(View.GONE);
			bytime_on.setVisibility(View.VISIBLE);
			byhot_off.setVisibility(View.VISIBLE);
			byhot_on.setVisibility(View.GONE);
			data.clear();
			pageIndex = -1;
			orderby = 1;
			getData();
			break;
		case R.id.byhot_off:
			bytime_off.setVisibility(View.VISIBLE);
			bytime_on.setVisibility(View.GONE);
			byhot_off.setVisibility(View.GONE);
			byhot_on.setVisibility(View.VISIBLE);
			data.clear();
			pageIndex = -1;
			orderby = 2;
			getData();
			break;
		case R.id.back:
			finish();
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
		}
	}

	private void getData() {
		// TODO Auto-generated method stub
		loadingPBar.setVisibility(View.VISIBLE);
		this.doAsync( null,
				new Callable<ArrayList<Display>>() {

					@Override
					public ArrayList<Display> call() throws Exception {
						// TODO Auto-generated method stub
						try {
							return new SHVolunteerApiImpl().getAllMeinList(
									++pageIndex, pageSize, 0, orderby);
						} catch (WSError e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}
				}, new Callback<ArrayList<Display>>() {

					@Override
					public void onCallback(ArrayList<Display> pCallbackValue) {
						// TODO Auto-generated method stub
						loadingPBar.setVisibility(View.INVISIBLE);
						if (pCallbackValue != null) {
							data.addAll(pCallbackValue);
							mAdapter.setList(data);
							mAdapter.notifyDataSetChanged();
						} else {
							showToast("没数据了");
						}
						/*if(data.size() > 0)
							noPhotoDisplayIV.setVisibility(View.GONE);
						else
							noPhotoDisplayIV.setVisibility(View.VISIBLE);*/
					}
				});
	}
}
