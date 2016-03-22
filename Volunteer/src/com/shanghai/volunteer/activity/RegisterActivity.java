package com.shanghai.volunteer.activity;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.activity.util.ValidUtil;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class RegisterActivity extends BaseActivity {

	private EditText password, reg_sure_password, phonenum, idCard, userName,
			checknum;
	private TextView register, toMobile, read, sendagain, next, sendwait;
	private LinearLayout text_toMobile, isReadLin, intoData;
	private ViewSwitcher isReaded;
	private boolean flag = true;
	private String mMobile;
	private String pwd;
	private String nickName;
	private String cardId;

	private int recLen = 60;
	Timer timer = new Timer();

	TimerTask task = new TimerTask() {
		@Override
		public void run() {

			runOnUiThread(new Runnable() { // UI thread
				@Override
				public void run() {
					recLen--;
					sendwait.setText("重新发送(" + recLen + ")");
					if (recLen < 0 && register.getVisibility() == View.GONE) {
						timer.cancel();
						sendwait.setVisibility(View.GONE);
						sendagain.setVisibility(View.VISIBLE);
					}
				}
			});
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newregister);
		findView();
	}

	private void findView() {
		// TODO Auto-generated method stub
		isReaded = (ViewSwitcher) findViewById(R.id.isReaded);
		text_toMobile = (LinearLayout) findViewById(R.id.text_toMobile);
		intoData = (LinearLayout) findViewById(R.id.intoData);
		isReadLin = (LinearLayout) findViewById(R.id.isReadLin);
		toMobile = (TextView) findViewById(R.id.toMobile);
		read = (TextView) findViewById(R.id.read);
		sendagain = (TextView) findViewById(R.id.sendagain);
		next = (TextView) findViewById(R.id.next);
		register = (TextView) findViewById(R.id.register);
		sendwait = (TextView) findViewById(R.id.sendwait);
		password = (EditText) findViewById(R.id.reg_password);
		checknum = (EditText) findViewById(R.id.checknum);
		checknum.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if ((checknum.getText().toString()).length() >3) {
					sendwait.setVisibility(View.GONE);
					register.setVisibility(View.VISIBLE);
				} else {
					sendwait.setVisibility(View.VISIBLE);
					register.setVisibility(View.GONE);
				}
			}
		});
		phonenum = (EditText) findViewById(R.id.reg_phonenum);
		idCard = (EditText) findViewById(R.id.reg_idCard);
		userName = (EditText) findViewById(R.id.reg_userName);
		reg_sure_password = (EditText) findViewById(R.id.reg_sure_password);
		findViewById(R.id.back).setOnClickListener(this);
		register.setOnClickListener(this);
		isReaded.setOnClickListener(this);
		read.setOnClickListener(this);
		next.setOnClickListener(this);
		sendagain.setOnClickListener(this);
	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.register:
			checkCodeIsTrue(phonenum.getText().toString(), checknum.getText()
					.toString());
			break;
		case R.id.next:
			mMobile = phonenum.getText().toString();
			pwd = password.getText().toString();
			nickName = userName.getText().toString();
			cardId = idCard.getText().toString();
			if (mMobile.isEmpty() || pwd.isEmpty()
					|| reg_sure_password.getText().toString().isEmpty()
					|| nickName.isEmpty() || cardId.isEmpty()) {
				showToast("信息不能为空");
				return;
			}
			if (!pwd.equals(reg_sure_password.getText().toString())) {
				showToast("密码确认错误");
				reg_sure_password.setText("");
				return;
			}
			if(!ValidUtil.isMobileNO(mMobile)){
				showToast("手机格式不正确");
				return;
			}
			if(!ValidUtil.IDCardValidate(cardId)){
				showToast("身份证格式不正确");
				return;
			}
			if (!flag) {
				showToast("请先同意相关条款");
				return;
			}
			getCheckNum(mMobile);
			break;
		case R.id.sendagain:
			getCheckNum(mMobile);
			break;
		case R.id.isReaded:
			if (flag) {
				isReaded.showNext();
				flag = false;
			} else {
				isReaded.showPrevious();
				flag = true;
			}
			break;
		case R.id.read: {
			Intent intent = new Intent(this, ArticleActivity.class);
			startActivity(intent);
			break;
		}
		}

	}

	private void checkCodeIsTrue(final String moblie, final String codeNum) {
		// TODO Auto-generated method stub
		this.doAsync("数据提交中...", new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				try {
					return new SHVolunteerApiImpl()
							.CheckIsTrue(moblie, codeNum);
				} catch (WSError e) {
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<Integer>() {

			@Override
			public void onCallback(Integer pCallbackValue) {
				switch (pCallbackValue) {
				case 1:
					showToast("验证成功");
					register(mMobile, pwd, cardId, nickName);
					break;
				default:
					showToast("验证失败");
					break;
				}
			}
		});
	}

	private void getCheckNum(final String mobile) {
		// TODO Auto-generated method stub
		this.doAsync("数据提交中...", new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				try {
					return new SHVolunteerApiImpl().CreateCheckCode(mobile);
				} catch (WSError e) {
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<Integer>() {

			@Override
			public void onCallback(Integer pCallbackValue) {
				switch (pCallbackValue) {
				case 1:
					showToast("消息已发送成功");
					text_toMobile.setVisibility(View.VISIBLE);
					toMobile.setText("发送到  " + mMobile);
					checknum.setVisibility(View.VISIBLE);
					sendwait.setVisibility(View.VISIBLE);
					next.setVisibility(View.GONE);
					intoData.setVisibility(View.GONE);
					isReadLin.setVisibility(View.GONE);
					timer.schedule(task, 1000, 1000); // timeTask
					break;
				default:
					showToast("消息发送失败");
					break;
				}
			}
		});
	}

	public void register(final String mobile, final String pwd,
			final String idCard, final String userName) {
		this.doAsync("数据上传中，请稍候...", new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().register(mobile, pwd,
							idCard, userName);
				} catch (WSError e) {
					e.printStackTrace();
				}
				return null;
			}

		}, new Callback<Integer>() {

			@Override
			public void onCallback(Integer pCallbackValue) {
				// TODO Auto-generated method stub
				if (pCallbackValue == 0) {
					showToast("注册成功！");
					finish();
				} else {
					showToast("注册失败！");
				}
			}
		});
	}

}
