package com.shanghai.volunteer.activity;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class ChangePwdActivity extends BaseActivity {

	private EditText password, phonenum, checknum;
	private LinearLayout text_toMobile;
	private FrameLayout frame_password;
	private TextView getCheckNum, next, begainChange, toMobile, sendagain,
			sendwait, title;
	private String mMobile;
	private String mCodeNum;
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
					if (recLen < 0 && begainChange.getVisibility() == View.GONE) {
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
		setContentView(R.layout.activity_forgetpwd);
		findView();
	}

	private void findView() {
		// TODO Auto-generated method stub
		findViewById(R.id.back).setOnClickListener(this);
		title = (TextView) findViewById(R.id.title);
		title.setText("修改密码");
		text_toMobile = (LinearLayout) findViewById(R.id.text_toMobile);
		toMobile = (TextView) findViewById(R.id.toMobile);
		frame_password = (FrameLayout) findViewById(R.id.frame_password);
		getCheckNum = (TextView) findViewById(R.id.getCheckNum);
		sendagain = (TextView) findViewById(R.id.sendagain);
		sendwait = (TextView) findViewById(R.id.sendwait);
		next = (TextView) findViewById(R.id.next);
		begainChange = (TextView) findViewById(R.id.begainChange);
		password = (EditText) findViewById(R.id.password);
		phonenum = (EditText) findViewById(R.id.phonenum);
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
				if ((checknum.getText().toString()).length() == 6) {
					sendwait.setVisibility(View.GONE);
					next.setVisibility(View.VISIBLE);
				} else {
					sendwait.setVisibility(View.VISIBLE);
					next.setVisibility(View.GONE);
				}
			}
		});
		getCheckNum.setOnClickListener(this);
		next.setOnClickListener(this);
		begainChange.setOnClickListener(this);
		sendagain.setOnClickListener(this);
		sendwait.setOnClickListener(this);
	}

	public boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	// 判断email格式是否正确
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	private void getCheckNum(final String mobile) {
		// TODO Auto-generated method stub
		if (isMobileNO(mobile)) {
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
						phonenum.setVisibility(View.GONE);
						getCheckNum.setVisibility(View.GONE);
						text_toMobile.setVisibility(View.VISIBLE);
						toMobile.setText("发送到  " + mMobile);
						checknum.setVisibility(View.VISIBLE);
						sendwait.setVisibility(View.VISIBLE);
						timer.schedule(task, 1000, 1000); // timeTask
						break;
					default:
						showToast("消息发送失败");
						break;
					}
				}
			});
		} else {
			showToast("请输入正确的手机号");
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
					text_toMobile.setVisibility(View.GONE);
					checknum.setVisibility(View.GONE);
					next.setVisibility(View.GONE);
					frame_password.setVisibility(View.VISIBLE);
					begainChange.setVisibility(View.VISIBLE);
					mCodeNum = checknum.getText().toString();
					break;
				default:
					showToast("验证失败");
					break;
				}
			}
		});
	}

	public void begainChange(final String msgCode, final String phoneNum,
			final String newPwd) {

		this.doAsync("数据提交中...", new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				try {
					return new SHVolunteerApiImpl().ForgetPwd(msgCode,
							phoneNum, newPwd);
				} catch (WSError e) {
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<Boolean>() {

			@Override
			public void onCallback(Boolean pCallbackValue) {
				if (pCallbackValue) {
					showToast("修改成功！");
					finish();
				} else {
					showToast("修改失败！");
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
		case R.id.getCheckNum:
			mMobile = phonenum.getText().toString();
			getCheckNum(mMobile);
			break;
		case R.id.next:
			checkCodeIsTrue(mMobile, checknum.getText().toString());
			break;
		case R.id.begainChange:
			begainChange(mCodeNum, mMobile, password.getText().toString());
			break;
		case R.id.sendagain:
			getCheckNum(mMobile);
			break;
		}
	}

}
