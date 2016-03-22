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
	 * �û���¼
	 * 
	 * @param mobile
	 * @param password
	 * @return
	 * @throws WSError
	 */
	public Account verify(String mobile, String password) throws WSError;

	/**
	 * ��ȡ�����б�
	 * 
	 * @return
	 * @throws WSError
	 * **/
	public ArrayList<News> getNewsList(int pageIndex, int pageSize)
			throws WSError;

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 * @throws WSError
	 * **/
	public News getDetailNews(String id) throws WSError;

	/**
	 * ��ȡ��б�
	 * 
	 * @return
	 * @throws WSError
	 * **/
	public ArrayList<Active> getActiveList(int pageIndex, int pageSize)
			throws WSError;

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 * @throws WSError
	 * **/
	public Active getDetailActive(String id) throws WSError;

	/**
	 * �μӻ
	 * 
	 * @return
	 * @throws WSError
	 * **/
	public int applyItem(String uId, String itemId) throws WSError;

	/**
	 * ע��
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
	 * ǩ��
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
	 * ��ȡ��֤��
	 * 
	 * @return 1 ʧ�ܣ�0 �ɹ�
	 **/
	public Integer CreateCheckCode(String mobile) throws WSError;

	/**
	 * Ч����֤���Ƿ���ȷ
	 * 
	 * @return 1ʧ�ܣ�0�ɹ�
	 * 
	 * **/
	public Integer CheckIsTrue(String mobile, String msgCode) throws WSError;

	/**
	 * �޸�����
	 * 
	 * @return falseʧ�ܣ�true�ɹ�
	 * **/
	public Boolean ForgetPwd(String msgCode, String newPwd, String uid)
			throws WSError;

	/**
	 * ����������
	 * 
	 * @return 0ʧ�ܣ�1�ɹ�
	 * **/
	public int addMein(String uId, String uName, String title, String txt,
			String image) throws WSError;

	/**
	 * ��ȡ��ҳ��Ϣ
	 * 
	 * @return homeData
	 * **/
	public MainPage GetHomePage() throws WSError;

	/**
	 * ��ȡ�������
	 * 
	 * @return Display
	 * **/
	public Display getDetailDisplay(String mId) throws WSError;

	/**
	 * �޲�
	 * 
	 * @return 0��ʾʧ�� 1��ʾ�ɹ� 2��ʾ�Ѿ����̬
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
