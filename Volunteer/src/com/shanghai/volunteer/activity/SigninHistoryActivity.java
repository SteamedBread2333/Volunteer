package com.shanghai.volunteer.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.activity.util.DateUtil;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class SigninHistoryActivity extends BaseActivity {

	private ViewGroup datelayout;
	private ImageView prevBtn, nextBtn, back;
	private Calendar c;
	private int currYear;
	private int currMonth;
	private TextView currentDateTV, title;
	private ArrayList<String> weekst = Constants.intoWeekSt();
	private ArrayList<TextView> dayTextViews = new ArrayList<TextView>();
	private ArrayList<Boolean> monthday = new ArrayList<Boolean>();
	int startIndex;

	private void intoM(int year,int month,int dayCount) {
		// TODO Auto-generated method stub
		monthday.clear();
		for (int i = 0; i < dayCount; i++) {
			monthday.add(false);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signinhistory);
		prevBtn = (ImageView) findViewById(R.id.prevBtn);
		nextBtn = (ImageView) findViewById(R.id.nextBtn);
		back = (ImageView) findViewById(R.id.back);
		title = (TextView) findViewById(R.id.title);
		title.setText("签到记录");
		currentDateTV = (TextView) findViewById(R.id.currentDateTV);

		prevBtn.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
		back.setOnClickListener(this);
		datelayout = (ViewGroup) findViewById(R.id.datelayout);

		initWeek();

		c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		currYear = c.get(Calendar.YEAR);
		currMonth = c.get(Calendar.MONTH) + 1;
		getData(Constants.mAccount.getUserID(), currYear + "-" + currMonth
				+ "-01" );

	}

	private void getData(final String UserId, final String date) {
		// TODO Auto-generated method stub

		this.doAsync("数据加载中，请稍候...", new Callable<ArrayList<Integer>>() {

			@Override
			public ArrayList<Integer> call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().GetSignInList(UserId, date);
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<ArrayList<Integer>>() {

			@Override
			public void onCallback(ArrayList<Integer> pCallbackValue) {
				int dayCount = DateUtil.getMonthDay(currYear, currMonth);
				// TODO Auto-generated method stub
				if (pCallbackValue != null) {
					
					intoM(currYear,currMonth,dayCount);
					for (int i = 0; i < pCallbackValue.size(); i++) {
						int day = pCallbackValue.get(i);
						for (int j = 0; j < dayCount; j++) {
							monthday.set(day - 1, true);
						}
					}
				} else {
					intoM(currYear,currMonth,dayCount);
				}
				initDays(currYear, currMonth,dayCount);
			}
		});
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.prevBtn:
			if (currMonth - 1 >= 1) {
				currMonth -= 1;
			} else {
				currYear -= 1;
				currMonth = 12;
			}
			getData(Constants.mAccount.getUserID(), currYear + "-" + currMonth
					+ "-" + 1);
			break;
		case R.id.nextBtn:
			if (currMonth + 1 > 12) {
				currYear += 1;
				currMonth = 1;
			} else {
				currMonth += 1;
			}
			getData(Constants.mAccount.getUserID(), currYear + "-" + currMonth
					+ "-" + 1);
			break;
		case R.id.back:
			finish();
			break;
		}
	}

	private void initWeek() {
		ViewGroup weeklayout = (ViewGroup) datelayout.getChildAt(0);
		for (int i = 0; i < weeklayout.getChildCount(); i++) {
			TextView dayTv = (TextView) ((ViewGroup) (weeklayout.getChildAt(i)))
					.getChildAt(0);
			dayTv.setText(weekst.get(i));
			dayTv.setTextColor(Color.WHITE);
		}
		weeklayout.setBackgroundResource(R.color.morelight_blue);
	}

	private void initDays(int year, int month,int dayCount) {
		currentDateTV.setText("" + year + "." + month);
		for (TextView tv : dayTextViews) {
			tv.setText("");
			tv.setBackground(null);
		}
		int row = datelayout.getChildCount() - 1;
		for (int i = 1; i <= row; i++) {
			ViewGroup weeklayout = (ViewGroup) datelayout.getChildAt(i);
			for (int j = 0; j < weeklayout.getChildCount(); j++) {
				TextView weekTv = (TextView) ((ViewGroup) (weeklayout
						.getChildAt(j))).getChildAt(0);
				dayTextViews.add(weekTv);
			}
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		try {
			c.setTime(format.parse(year + "." + month + "." + 1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int week = c.get(Calendar.DAY_OF_WEEK);
		week = (week == 1 ? 1 : week - 1);

		startIndex = week;
		for (int dayIndex = 0; dayIndex < dayCount; dayIndex++) {
			dayTextViews.get(startIndex).setText("" + (dayIndex + 1));
				if (monthday.get(dayIndex)) {
					dayTextViews.get(startIndex).setBackgroundResource(
						R.drawable.checkmark);
			}
			startIndex++;
		}

	}

}
