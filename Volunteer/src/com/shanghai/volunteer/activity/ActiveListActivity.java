package com.shanghai.volunteer.activity;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.activity.view.RTPullListView;
import com.shanghai.volunteer.activity.view.RTPullListView.OnRefreshListener;
import com.shanghai.volunteer.adapter.ActiveAdapter;
import com.shanghai.volunteer.bean.Active;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class ActiveListActivity extends BaseActivity {
	private ImageView back, personl,noActivityIV;
	private TextView title;
	private ListView activeLV;
	private ActiveAdapter adapter;
	private ArrayList<Active> data = new ArrayList<Active>();
	private Intent intent;

	private int pageIndex = -1;
	private int pageSize = 10;
	private ProgressBar loadingPBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_objlistview);
		findView();
	}

	public void getData() {
		loadingPBar.setVisibility(View.VISIBLE);
		this.doAsync(null,
				new Callable<ArrayList<Active>>() {

					@Override
					public ArrayList<Active> call() throws Exception {
						// TODO Auto-generated method stub
						try {
							return new SHVolunteerApiImpl().getActiveList(
									++pageIndex, pageSize);
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
						loadingPBar.setVisibility(View.INVISIBLE);
						if (pCallbackValue != null && pCallbackValue.size() > 0) {
							data.addAll(pCallbackValue);
							adapter.setData(data);
							adapter.notifyDataSetChanged();
						}else{
							showToast("无数据了");
						}
						if(data.size() > 0)
							noActivityIV.setVisibility(View.GONE);
						else
							noActivityIV.setVisibility(View.VISIBLE);
					}
				});
	}

	private void findView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.back);
		personl = (ImageView) findViewById(R.id.personl);
		title = (TextView) findViewById(R.id.title);
		title.setText("志愿招募");
		noActivityIV = (ImageView) findViewById(R.id.noActivityIV);
		//listView
		activeLV = (ListView) findViewById(R.id.obLV);
		//add footer
		View view = LayoutInflater.from(this).inflate(
				R.layout.footer_loading_layout, null);
		loadingPBar = (ProgressBar) view.findViewById(R.id.footerLoadingPbar);
		activeLV.addFooterView(view);
		
		activeLV.setOnItemClickListener(this);
		back.setOnClickListener(this);
		personl.setOnClickListener(this);
		adapter = new ActiveAdapter(this);
		adapter.setData(data);
		activeLV.setAdapter(adapter);
		
		activeLV.setOnScrollListener(new OnScrollListener() {

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
					break;
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
	public void onItemClick(AdapterView<?> adapterView, View view, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		super.onItemClick(adapterView, view, arg2, arg3);
		intent = new Intent(this, DetailActiverActivity.class);
		intent.putExtra("activeId", data.get(arg2).getID());
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
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
}
