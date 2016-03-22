package com.shanghai.volunteer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;

public class DetailMissionActivity extends BaseActivity {

	private TextView title, isMissionComplete;
	private ImageView back, personl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailactive);
		findView();
	}

	private void findView() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.title);
		title.setText("项目任务详情");
		isMissionComplete = (TextView) findViewById(R.id.detailActivegotojoin);
		int px = Constants.dip2px(this, 10);
		if (getIntent().getIntExtra("Status", 1) == 1) {
			isMissionComplete
					.setBackgroundResource(R.drawable.textview_bg_green_round);
			isMissionComplete.setPadding(px, px, px, px);
			isMissionComplete.setText("执行中");
		} else if (getIntent().getIntExtra("Status", 1) == 2) {
			isMissionComplete
					.setBackgroundResource(R.drawable.textview_bg_red_round);
			isMissionComplete.setPadding(px, px, px, px);
			isMissionComplete.setText("已结束");
		}
		back = (ImageView) findViewById(R.id.back);
		personl = (ImageView) findViewById(R.id.personl);
		back.setOnClickListener(this);
		personl.setOnClickListener(this);
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
			break;
		}
	}

}
