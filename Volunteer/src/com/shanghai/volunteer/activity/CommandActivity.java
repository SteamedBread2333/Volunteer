package com.shanghai.volunteer.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.adapter.CommandAdapter;
import com.shanghai.volunteer.bean.MeinCommend;
import com.shanghai.volunteer.bean.OnlineAdvice;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class CommandActivity extends BaseActivity {

	private ImageView back, person;
	private TextView CommandSend, title;
	private EditText CommandET;
	private ListView CommandLV;
	private CommandAdapter myadapter = null;
	ArrayList<MeinCommend> data = new ArrayList<MeinCommend>();
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_command);
		findView();
	}

	private void findView() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.title);
		title.setText("评论");
		back = (ImageView) findViewById(R.id.back);
		person = (ImageView) findViewById(R.id.personl);
		CommandSend = (TextView) findViewById(R.id.CommandSend);
		CommandET = (EditText) findViewById(R.id.CommandET);
		CommandLV = (ListView) findViewById(R.id.CommandLV);
		myadapter = new CommandAdapter(CommandActivity.this);
		myadapter.setData(new ArrayList<MeinCommend>());
		CommandLV.setAdapter(myadapter);
		// footer
		View footerView = new View(this);
		footerView.setMinimumHeight(Constants.dip2px(this, 60));
		CommandLV.addFooterView(footerView);
		back.setOnClickListener(this);
		person.setOnClickListener(this);
		CommandSend.setOnClickListener(this);
		getData();
	}

	private void getData() {
		// TODO Auto-generated method stub
		this.doAsync(new Callable<ArrayList<MeinCommend>>() {

			@Override
			public ArrayList<MeinCommend> call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().getMeinReview(getIntent()
							.getStringExtra("mId"));
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<ArrayList<MeinCommend>>() {

			@Override
			public void onCallback(ArrayList<MeinCommend> pCallbackValue) {
				// TODO Auto-generated method stub

				if (pCallbackValue != null) {
					data.addAll(pCallbackValue);
					myadapter.setData(data);
					myadapter.notifyDataSetChanged();
				}
			}
		});
	}

	private void sendMessage() {
		// TODO Auto-generated method stub
		this.doAsync("数据提交中，请稍候...", new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().addMeinReview(getIntent()
							.getStringExtra("mId"), Constants.mAccount
							.getUserID(), Constants.mAccount.getNickName(),
							CommandET.getText().toString());
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<Integer>() {

			@Override
			public void onCallback(Integer pCallbackValue) {
				// TODO Auto-generated method stub
				if (pCallbackValue == 1) {
					showToast("提交成功");
					SimpleDateFormat sDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd	 hh:mm:ss");
					String date = sDateFormat.format(new java.util.Date());
					if (data.isEmpty()) {
						myadapter.getData().add(
								new MeinCommend(Constants.mAccount
										.getNickName(), CommandET.getText()
										.toString(), date, Constants.mAccount
										.getUserImg()));
						/*
						 * data.add(new MeinCommend(Constants.mAccount
						 * .getNickName(), CommandET.getText().toString(), date,
						 * Constants.mAccount.getUserImg()));
						 */
					} else {
						/*
						 * data.add( 0, new MeinCommend(Constants.mAccount
						 * .getNickName(), CommandET.getText() .toString(),
						 * date, Constants.mAccount .getUserImg()));
						 */
						myadapter.getData().add(
								0,
								new MeinCommend(Constants.mAccount
										.getNickName(), CommandET.getText()
										.toString(), date, Constants.mAccount
										.getUserImg()));
					}
					// myadapter.setData(data);
					myadapter.notifyDataSetChanged();
					CommandET.setText("");
				} else {
					showToast("提交失败");
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
		case R.id.CommandSend:
			if (!CommandET.getText().toString().isEmpty()) {
				sendMessage();
			}
			break;
		}
	}
}
