package com.shanghai.volunteer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.shanghai.volunteer.bean.Account;
import com.shanghai.volunteer.bean.Active;
import com.shanghai.volunteer.bean.News;

import android.content.Context;
import android.os.Environment;

public class Constants {

	public static int ScreenWidth = 0;
	public static int ScreenHeight = 0;
	public static float ScreenDensity = 0;
	public static Account mAccount = null;
	public static String WXappID = "wx28e97c3fda641315";
	public static String WXSecret = "b62cba13ccd5e1f298b18b276b52151c";
	public static ArrayList<String> weekSt = new ArrayList<String>();
	public static String DEFAULT_PHOTO_URL = "http://shapp.hzit.org/UserIcon/default_photo.jpg";
	public static ArrayList<String> intoWeekSt() {
		// TODO Auto-generated method stub
		weekSt.add("日");
		weekSt.add("一");
		weekSt.add("二");
		weekSt.add("三");
		weekSt.add("四");
		weekSt.add("五");
		weekSt.add("六");
		return weekSt;
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static byte[] getLocalImage2ByteArray(String filePath) {
		byte[] data = null;
		File file = null;
		FileInputStream fis = null;
		try {
			file = new File(filePath);
			if (file != null) {
				fis = new FileInputStream(file);
				data = new byte[fis.available()];
				fis.read(data);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return data;
	}

}
