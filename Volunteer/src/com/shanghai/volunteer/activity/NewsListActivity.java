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
import com.shanghai.volunteer.adapter.NewsListAdapter;
import com.shanghai.volunteer.bean.News;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class NewsListActivity extends BaseActivity {

	private ImageView back, personl,noActivityIV;
	private TextView title;
	private ListView newslistLV;
	private NewsListAdapter adapter;
	private ArrayList<News> data = new ArrayList<News>();
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
		getData();
	}

	public void getData() {
		loadingPBar.setVisibility(View.VISIBLE);
		this.doAsync(null, new Callable<ArrayList<News>>() {

			@Override
			public ArrayList<News> call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().getNewsList(++pageIndex,
							pageSize);
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<ArrayList<News>>() {

			@Override
			public void onCallback(ArrayList<News> pCallbackValue) {
				// TODO Auto-generated method stub
				loadingPBar.setVisibility(View.INVISIBLE);
				if (pCallbackValue != null && pCallbackValue.size() > 0) {
					data.addAll(pCallbackValue);
					adapter.setData(data);
					adapter.notifyDataSetChanged();
				} else {
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
		noActivityIV = (ImageView) findViewById(R.id.noActivityIV);
		noActivityIV.setImageResource(R.drawable.no_network);
		title = (TextView) findViewById(R.id.title);
		title.setText("新闻");
		newslistLV = (ListView) findViewById(R.id.obLV);
		// add footer
		View view = LayoutInflater.from(this).inflate(
				R.layout.footer_loading_layout, null);
		loadingPBar = (ProgressBar) view.findViewById(R.id.footerLoadingPbar);
		newslistLV.addFooterView(view);
		
		newslistLV.setOnItemClickListener(this);
		back.setOnClickListener(this);
		personl.setOnClickListener(this);
		adapter = new NewsListAdapter(NewsListActivity.this);
		adapter.setData(data);
		newslistLV.setAdapter(adapter);

		newslistLV.setOnScrollListener(new OnScrollListener() {

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
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		super.onItemClick(adapterView, view, arg2, arg3);
		intent = new Intent(this, DetailNewsActivity.class);
		intent.putExtra("newsId", data.get(arg2).getID());
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
