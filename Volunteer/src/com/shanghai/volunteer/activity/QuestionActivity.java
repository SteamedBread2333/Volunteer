package com.shanghai.volunteer.activity;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.adapter.QuestionAdapter;
import com.shanghai.volunteer.bean.Question;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class QuestionActivity extends BaseActivity {

	private ListView questionLV;
	private QuestionAdapter adapter;
	private ImageView back, personl,noQuestionDisplayIV;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		findView();
		getData();
	}

	private void findView() {
		// TODO Auto-generated method stub
		questionLV = (ListView) findViewById(R.id.questionLV);
		adapter = new QuestionAdapter(this);
		adapter.setData(new ArrayList<Question>());
		questionLV.setAdapter(adapter);
		back = (ImageView) findViewById(R.id.back);
		noQuestionDisplayIV = (ImageView) findViewById(R.id.noQuestionDisplayIV);
		back.setOnClickListener(this);
		personl = (ImageView) findViewById(R.id.personl);
		personl.setOnClickListener(this);
	}

	private void getData() {
		// TODO Auto-generated method stub
		this.doAsync("数据加载中，请稍候..." , new Callable<ArrayList<Question>>() {

			@Override
			public ArrayList<Question> call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().getFAQ();
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<ArrayList<Question>>() {

			@Override
			public void onCallback(ArrayList<Question> pCallbackValue) {
				// TODO Auto-generated method stub
				if(pCallbackValue != null && pCallbackValue.size() > 0){
					noQuestionDisplayIV.setVisibility(View.GONE);
					adapter.setData(pCallbackValue);
					adapter.notifyDataSetChanged();
				}else{
					noQuestionDisplayIV.setVisibility(View.VISIBLE);
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
