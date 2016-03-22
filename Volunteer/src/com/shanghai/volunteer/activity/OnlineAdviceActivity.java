package com.shanghai.volunteer.activity;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.adapter.OnlineAdviceListAdapter;
import com.shanghai.volunteer.bean.OnlineAdvice;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class OnlineAdviceActivity extends BaseActivity {
	private ListView adviceListView;
	private OnlineAdviceListAdapter adapter;
	private EditText AdviceET;
	private TextView AdviceSend;
	private int pageIndex = -1;
	private int pageSize = 10;
	private ProgressBar loadingPBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_online_advice);
		AdviceET = (EditText) findViewById(R.id.AdviceET);
		AdviceSend = (TextView) findViewById(R.id.AdviceSend);
		AdviceSend.setOnClickListener(this);
		// title
		findViewById(R.id.back).setOnClickListener(this);
		((TextView) findViewById(R.id.title)).setText("咨询建议");
		// listView
		adviceListView = (ListView) findViewById(R.id.adviceListView);
		// seting footer
		View view = LayoutInflater.from(this).inflate(
				R.layout.footer_loading_layout, null);
		loadingPBar = (ProgressBar) view.findViewById(R.id.footerLoadingPbar);
		adviceListView.addFooterView(view);
		// setting adapter
		adapter = new OnlineAdviceListAdapter(this);
		adapter.setData(new ArrayList<OnlineAdvice>());
		adviceListView.setAdapter(adapter);
		adviceListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				// 当不滚动时
				case OnScrollListener.SCROLL_STATE_IDLE:
					// 判断滚动到底部
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
						// init data
						initData();
					}
					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		// init data
		initData();
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.AdviceSend:
			if (!AdviceET.getText().toString().isEmpty()) {
				sendNewAdvice();
			}
			break;
		}
	}

	private void sendNewAdvice() {
		// TODO Auto-generated method stub
		this.doAsync(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().sendNewAdvice(
							Constants.mAccount.getUserID(), Constants.mAccount
									.getNickName(), AdviceET.getText()
									.toString());
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
					showToast("发表成功");
					adapter.getData().add(
							0,
							new OnlineAdvice(AdviceET.getText().toString(),
									Constants.mAccount.getUserImg()));
					adapter.notifyDataSetChanged();
					AdviceET.setText("");
				} else {
					showToast("发表失败");
				}
			}
		});
	}

	/**
	 * init data
	 * */
	private void initData() {
		loadingPBar.setVisibility(View.VISIBLE);
		this.doAsync(null, new Callable<ArrayList<OnlineAdvice>>() {

			@Override
			public ArrayList<OnlineAdvice> call() throws Exception {
				// TODO Auto-generated method stub
				try {
					ArrayList<OnlineAdvice> onlineAdviceArray = new SHVolunteerApiImpl()
							.getOnlieAdviceList(++pageIndex, pageSize,
									Constants.mAccount.getUserID());
					return onlineAdviceArray;
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<ArrayList<OnlineAdvice>>() {

			@Override
			public void onCallback(ArrayList<OnlineAdvice> pCallbackValue) {
				// TODO Auto-generated method stub
				loadingPBar.setVisibility(View.INVISIBLE);
				if (pCallbackValue != null && pCallbackValue.size() > 0) {
					adapter.getData().addAll(pCallbackValue);
					// adapter.getData().add(0, object)
					// adapter.setData(adapter.getData());
					adapter.notifyDataSetChanged();
				} else {

					showToast("无数据了");
				}
			}
		}, new Callback<Exception>() {

			@Override
			public void onCallback(Exception e) {

			}
		});

	}
}
