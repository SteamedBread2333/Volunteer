package com.shanghai.volunteer.activity;

import java.util.concurrent.Callable;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.bean.News;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;
import com.shanghai.volunteer.other.parallax.ParallaxScrollView;

public class DetailNewsActivity extends BaseActivity {

	private ImageView back;
	private WebView dnDetailWebview;
	private TextView title, dnTitleTV, dnAuthorTV, dnCreatTimeTV;

	// private ParallaxScrollView dnPSC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailnews);
		findView();
	}

	private void findView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.back);
		// dnIM = (ImageView) findViewById(R.id.dnIM);
		title = (TextView) findViewById(R.id.title);
		dnTitleTV = (TextView) findViewById(R.id.dnTitleTV);
		dnAuthorTV = (TextView) findViewById(R.id.dnAuthorTV);
		dnCreatTimeTV = (TextView) findViewById(R.id.dnCreatTimeTV);
		dnDetailWebview = (WebView) findViewById(R.id.dnDetailWebview);
		// dnPSC = (ParallaxScrollView) findViewById(R.id.dnPSC);
		back.setOnClickListener(this);
		title.setText("新闻详情");
		getData();
		// dnPSC.setImageViewToParallax(dnIM);
	}

	private void getData() {
		// TODO Auto-generated method stub

		this.doAsync("数据加载中，请稍候...", new Callable<News>() {

			@Override
			public News call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().getDetailNews(getIntent()
							.getStringExtra("newsId"));
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<News>() {

			@Override
			public void onCallback(News pCallbackValue) {
				// TODO Auto-generated method stub
				/*
				 * ImageLoader.getInstance().displayImage(pCallbackValue.getImg()
				 * , dnIM);
				 */
				if (pCallbackValue != null) {
					dnTitleTV.setText(pCallbackValue.getTitle());
					dnAuthorTV.setText(pCallbackValue.getAuthor());
					dnCreatTimeTV.setText(pCallbackValue.getCreatTime());
					dnDetailWebview.loadDataWithBaseURL("about:blank",
							pCallbackValue.getDetails(), "text/html", "utf-8",
							null);
				}else{
					showToast("请检查网络连接！");
					finish();
				}
			}
		});

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		}
	}
}
