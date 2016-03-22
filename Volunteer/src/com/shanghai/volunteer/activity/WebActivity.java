package com.shanghai.volunteer.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;

public class WebActivity extends BaseActivity {

	private ImageView back;
	private TextView title;
	private WebView web;
	private TextView webDetailPromptTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		intoView();
	}
	private void intoView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.back);
		title = (TextView) findViewById(R.id.title);
		web = (WebView) findViewById(R.id.web);
		webDetailPromptTV = (TextView) findViewById(R.id.webDetailPromptTV);
		webDetailPromptTV.setText("º”‘ÿ÷–£¨«Î…‘∫Ó...");
		web.getSettings().setJavaScriptEnabled(true);
		web.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				showToast("«ÎºÏ≤ÈÕ¯¬Á¡¨Ω”£°");
				finish();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				webDetailPromptTV.setVisibility(View.GONE);
			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		web.loadUrl(getIntent().getStringExtra("url"));
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		title.setText(getIntent().getStringExtra("title"));
	}
}
