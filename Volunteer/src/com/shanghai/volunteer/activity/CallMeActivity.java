package com.shanghai.volunteer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.other.parallax.ParallaxScrollView;

public class CallMeActivity extends BaseActivity {

	private ImageView callme , back , personl;
	private TextView title;
	private ParallaxScrollView CallMeSV;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_callme);
		callme = (ImageView) findViewById(R.id.callme);
		title = (TextView) findViewById(R.id.title);
		back = (ImageView) findViewById(R.id.back);
		personl = (ImageView) findViewById(R.id.personl);
		personl.setVisibility(View.INVISIBLE);
		CallMeSV = (ParallaxScrollView) findViewById(R.id.CallMeSV);
		back.setOnClickListener(this);
		personl.setOnClickListener(this);
		title.setText("联系我们");
		ImageLoader.getInstance().displayImage(
				"drawable://" + R.drawable.callme, callme);
		CallMeSV.setImageViewToParallax(callme);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch(view.getId()){
		case R.id.back:
			finish();
			break;
		case R.id.personl:
			break;
		}
	}
}
