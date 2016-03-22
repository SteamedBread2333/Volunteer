package com.shanghai.volunteer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;

public class WebListActivity extends BaseActivity {

	private TextView web1, web2, web3, web4, title;
	private ImageView back;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weblist);
		web1 = (TextView) findViewById(R.id.web1);
		web2 = (TextView) findViewById(R.id.web2);
		web3 = (TextView) findViewById(R.id.web3);
		web4 = (TextView) findViewById(R.id.web4);
		title = (TextView) findViewById(R.id.title);
		title.setText("联通");
		back = (ImageView) findViewById(R.id.back);
		web1.setOnClickListener(this);
		web2.setOnClickListener(this);
		web3.setOnClickListener(this);
		web4.setOnClickListener(this);
		back.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.web1:
			intent = new Intent(this, WebActivity.class);
			intent.putExtra("url", "http://www.ishwap.com/navsh/my.html");
			intent.putExtra("title", "流量查询");
			startActivity(intent);
			break;
		case R.id.web2:
			intent = new Intent(this, WebActivity.class);
			intent.putExtra("url",
					"http://www.ishwap.com/u/zy5");
			intent.putExtra("title", "流量加油");
			startActivity(intent);
			break;
		case R.id.web3:
			intent = new Intent(this, WebActivity.class);
			intent.putExtra(
					"url",
					"http://wap.10010.com/t/customerService/queryPluginPortalEhallMap.htm?publish=publish&&menuId=000100010001");
			intent.putExtra("title", "网点查询");
			startActivity(intent);
			break;
		case R.id.web4:
			intent = new Intent(this, WebActivity.class);
			intent.putExtra("url", "http://m.10010.com");
			intent.putExtra("title", "手机商城");
			startActivity(intent);
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}

}
