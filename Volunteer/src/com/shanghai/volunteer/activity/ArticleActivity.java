package com.shanghai.volunteer.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;

public class ArticleActivity extends BaseActivity {

	TextView title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		title = (TextView) findViewById(R.id.title);
		title.setText("用户协议");
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
