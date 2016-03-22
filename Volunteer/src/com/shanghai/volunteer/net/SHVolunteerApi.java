package com.shanghai.volunteer.net;

import java.util.ArrayList;

import com.shanghai.volunteer.bean.Account;
import com.shanghai.volunteer.bean.Active;
import com.shanghai.volunteer.bean.Display;
import com.shanghai.volunteer.bean.MainPage;
import com.shanghai.volunteer.bean.MeinCommend;
import com.shanghai.volunteer.bean.News;
import com.shanghai.volunteer.bean.Question;

public interface SHVolunteerApi {
	/**
	 * 用户登录
	 * 
	 * @param mobile
	 * @param password
	 * @return
	 * @throws WSError
	 */
	public Account verify(String mobile, String password) throws WSError;

	/**
	 * 获取新闻列表
	 * 
	 * @return
	 * @throws WSError
	 * **/
	public ArrayList<News> getNewsList(int pageIndex, int pageSize)
			throws WSError;

	/**
	 * 获取新闻详情
	 * 
	 * @return
	 * @throws WSError
	 * **/
	public News getDetailNews(String id) throws WSError;

	/**
	 * 获取活动列表
	 * 
	 * @return
	 * @throws WSError
	 * **/
	public ArrayList<Active> getActiveList(int pageIndex, int pageSize)
			throws WSError;

	/**
	 * 获取新闻详情
	 * 
	 * @return
	 * @throws WSError
	 * **/
	public Active getDetailActive(String id) throws WSError;

	/**
	 * 参加活动
	 * 
	 * @return
	 * @throws WSError
	 * **/
	public int applyItem(String uId, String itemId) throws WSError;

	/**
	 * 注册
	 * 
	 * @param phoneNum
	 * @param psw
	 * @param idCard
	 * @param userName
	 * @return
	 * @throws WSError
	 * **/
	public Integer register(String phoneNum, String psw, String idCard,
			String userName) throws WSError;

	/**
	 * 签到
	 * 
	 * @param uId
	 * @param uName
	 * @param longitude
	 * @param latitude
	 * @return
	 * @throws WSError
	 * **/
	public int Signin(String uId, String uName, double longitude,
			double latitude) throws WSError;

	/**
	 * 获取验证码
	 * 
	 * @return 1 失败，0 成功
	 **/
	public Integer CreateCheckCode(String mobile) throws WSError;

	/**
	 * 效验验证码是否正确
	 * 
	 * @return 1失败，0成功
	 * 
	 * **/
	public Integer CheckIsTrue(String mobile, String msgCode) throws WSError;

	/**
	 * 修改密码
	 * 
	 * @return false失败，true成功
	 * **/
	public Boolean ForgetPwd(String msgCode, String newPwd, String uid)
			throws WSError;

	/**
	 * 发布随手拍
	 * 
	 * @return 0失败，1成功
	 * **/
	public int addMein(String uId, String uName, String title, String txt,
			String image) throws WSError;

	/**
	 * 获取首页信息
	 * 
	 * @return homeData
	 * **/
	public MainPage GetHomePage() throws WSError;

	/**
	 * 获取风采详情
	 * 
	 * @return Display
	 * **/
	public Display getDetailDisplay(String mId) throws WSError;

	/**
	 * 赞踩
	 * 
	 * @return 0表示失败 1表示成功 2表示已经表过态
	 * **/
	public int PraiseMein(String mId, String userId, int isPraise)
			throws WSError;

	public ArrayList<Active> getMyActiveList(String uId, int pageIndex,
			int pageSize) throws WSError;

	public ArrayList<Display> getAllMyMeinList(String uId, int pageIndex,
			int pageSize) throws WSError;

	public String updataUserIcon(String uId, String img) throws WSError;

	public String getUserIcon(String uId) throws WSError;

	public ArrayList<Active> getItemListByKey(int pageIndex, int pageSize,
			String keyword) throws WSError;

	public ArrayList<Active> getItemListByCoordinates(int pageIndex,
			int pageSize, String lng, String lat) throws WSError;
	public ArrayList<MeinCommend> getMeinReview(String mId) throws WSError;
	public ArrayList<Question> getFAQ() throws WSError;
	public int sendNewAdvice(String uId, String uName, String question)
			throws WSError;
}
