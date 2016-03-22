package com.shanghai.volunteer.activity.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {


	/**
	 * get week
	 * 
	 * @return week string
	 * */
	public static String getWeek() {
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
		Date curDate = new Date(System.currentTimeMillis());
		return formatter.format(curDate);
	}


	/**
	 * isLeapYear
	 * */
	public static int getMonthDay(int year, int month) {
		boolean isLeapYear = false;
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			isLeapYear = true;
		}
		if (isLeapYear && month == 2) {
			return 29;
		} else if (!isLeapYear && month == 2) {
			return 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		} else {
			return 31;
		}
	}
}
