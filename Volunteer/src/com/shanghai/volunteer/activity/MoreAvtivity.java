package com.shanghai.volunteer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;

public class MoreAvtivity extends BaseActivity {

	private TextView suishoupai, suggest, question, display;
	private ImageView back, personl;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		((TextView) findViewById(R.id.title)).setText("¸ü¶à");
		findView();

	}

	private void findView() {
		// TODO Auto-generated method stub
		suishoupai = (TextView) findViewById(R.id.suishoupai);
		suggest = (TextView) findViewById(R.id.suggest);
		question = (TextView) findViewById(R.id.question);
		display = (TextView) findViewById(R.id.display);
		back = (ImageView) findViewById(R.id.back);
		personl = (ImageView) findViewById(R.id.personl);
		suishoupai.setOnClickListener(this);
		suggest.setOnClickListener(this);
		question.setOnClickListener(this);
		back.setOnClickListener(this);
		display.setOnClickListener(this);
		personl.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.suishoupai:
			intent = new Intent(MoreAvtivity.this, GetSuishoupaiActivity.class);
			startActivity(intent);
			break;
		case R.id.suggest:
			if (Constants.mAccount == null) {
				intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				intent = new Intent(MoreAvtivity.this, OnlineAdviceActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.question:
			intent = new Intent(MoreAvtivity.this, QuestionActivity.class);
			startActivity(intent);
			break;
		case R.id.display:
			intent = new Intent(MoreAvtivity.this, DisplayListActivity.class);
			startActivity(intent);
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
}
