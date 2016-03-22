package com.shanghai.volunteer.activity;

import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.activity.util.ValidUtil;
import com.shanghai.volunteer.activity.view.SoftKeyBoardSatusView;
import com.shanghai.volunteer.activity.view.SoftKeyBoardSatusView.SoftkeyBoardListener;
import com.shanghai.volunteer.application.SHVolunteerApplication;
import com.shanghai.volunteer.bean.Account;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class LoginActivity extends BaseActivity implements OnFocusChangeListener,SoftkeyBoardListener{

	private ImageView back,loginIconImageView;
	private EditText phonenum, password;
	private TextView login, reg, forgetpwd;
	private Intent intent;
	SoftKeyBoardSatusView satusView;
	ScrollView scrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findView();
	}

	private void findView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.backBtn);
		phonenum = (EditText) findViewById(R.id.phonenum);
		loginIconImageView = (ImageView) findViewById(R.id.loginIconImageView);
		password = (EditText) findViewById(R.id.password);
		login = (TextView) findViewById(R.id.login);
		reg = (TextView) findViewById(R.id.reg);
		forgetpwd = (TextView) findViewById(R.id.forgetpwd);
		back.setOnClickListener(this);
		login.setOnClickListener(this);
		reg.setOnClickListener(this);
		forgetpwd.setOnClickListener(this);
		satusView=(SoftKeyBoardSatusView)findViewById(R.id.login_soft_status_view);
		satusView.setSoftKeyBoardListener(this);
		
		scrollView=(ScrollView)findViewById(R.id.login_scroller);
	}

	public boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	private void login(final String mobile, final String password) {

		if (ValidUtil.isMobileNO(mobile) || ValidUtil.IDCardValidate(mobile)) {
			this.doAsync("登录中...", new Callable<Account>() {

				@Override
				public Account call() throws Exception {
					try {
						return new SHVolunteerApiImpl()
								.verify(mobile, password);
					} catch (WSError e) {
						e.printStackTrace();
					}
					return null;
				}
			}, new Callback<Account>() {

				@Override
				public void onCallback(Account pCallbackValue) {
					if (pCallbackValue != null) {
						SHVolunteerApplication.getInstance().saveAccount(
								pCallbackValue);
						Constants.mAccount = pCallbackValue;
						showToast("登录成功");
						finish();
					} else {
						showToast("请输入正确账号密码...");
					}
				}
			});
		} else {
			showToast("请输入正确手机号");
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.backBtn:
			finish();
			break;
		case R.id.login:
			login(phonenum.getText().toString(), password.getText().toString());
			break;
		case R.id.reg:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		case R.id.forgetpwd:
			startActivity(new Intent(this, ForgetPwdActivity.class));
			break;
		}
	}

	@Override
	public void keyBoardStatus(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyBoardVisable(int move) {
		// TODO Auto-generated method stub
		login.getScrollY();
		Message message=new Message();
		message.what=WHAT_SCROLL;
		message.obj=move;
		handler.sendMessageDelayed(message, 500);
	}

	@Override
	public void keyBoardInvisable(int move) {
		// TODO Auto-generated method stub
		handler.sendEmptyMessageDelayed(WHAT_BTN_VISABEL, 200);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		
	}
	final int WHAT_SCROLL=0,WHAT_BTN_VISABEL=WHAT_SCROLL+1;
	Handler handler=new Handler(){

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			switch (msg.what) {
			case WHAT_SCROLL:
				int move=(Integer) msg.obj;
				//scrollView.smoothScrollBy(0, move);
				loginIconImageView.setVisibility(View.GONE);
				break;
			case WHAT_BTN_VISABEL:
				loginIconImageView.setVisibility(View.VISIBLE);
				break;
			}
		}
		
	};
}
