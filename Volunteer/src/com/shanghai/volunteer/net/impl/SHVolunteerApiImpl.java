package com.shanghai.volunteer.net.impl;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.bean.Account;
import com.shanghai.volunteer.bean.Active;
import com.shanghai.volunteer.bean.Display;
import com.shanghai.volunteer.bean.MainPage;
import com.shanghai.volunteer.bean.MeinCommend;
import com.shanghai.volunteer.bean.News;
import com.shanghai.volunteer.bean.OnlineAdvice;
import com.shanghai.volunteer.bean.Question;
import com.shanghai.volunteer.net.SHVolunteerApi;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.util.WebServicesUtil;

public class SHVolunteerApiImpl implements SHVolunteerApi {
	private static final String NAMESPACE = "http://shapp.hzit.org/";
	public static final String DOWNLOAD_URL = "http://shapp.hzit.org/";
	public static final String BANNER_DOWNLOAD_URL = "http://shappweb.hzit.org/img/";
	// 登录
	private static final String USER_URL = NAMESPACE + "VolunteerWS.asmx";
	private static final String LOG_IN = "UserLogIn"; // 登录
	private static final String GET_ALL_NEWS = "GetNews";// 获取所有新闻
	private static final String GET_DETAIL_NEWS = "GetNewsDetails";// 获取新闻详情
	private static final String GET_ALL_ACTIVE = "GetItemList";// 获取活动列表
	private static final String GET_ALL_MYACTIVE = "GetMyItem";// 获取我的活动列表
	private static final String GET_DETAIL_ACTIVE = "GetItemDetails";// 获取活动详情
	private static final String APPLY_ITEM = "ApplyItem";// 获取活动详情
	private static final String REGISTER = "Register";// 注册
	private static final String SIGN_IN = "SignIn";// 签到
	private static final String GET_CHECKCODE = "GetVerification";// 获取手机验证码
	private static final String CHECKCODE_ISTRUE = "Verification";// 检查验证码
	private static final String FINDPWD = "ChangePswWithCode";// 忘记密码
	private static final String ADDMEIN = "AddMein";// 发布随手拍
	private static final String GET_HOME_PAGE = "GetHomePage";// 获取首页信息
	private static final String GET_ALL_MEINLIST = "GetAllMeinList";// 获取风采展示列表
	private static final String GET_ALL_MYMEINLIST = "GetMyMeinList";// 获取我的风采展示列表
	private static final String GET_DETAIL_MEIN = "GetMeinDetails";// 获取风采展示详情
	private static final String PRAISE_MEIN = "PraiseMein";// 赞踩
	private static final String GET_SIGN_INLIST = "GetSignInList";// 获取签到记录
	private static final String UPDATA_USERICON = "UpdataUserIcon";// 获取签到记录
	private static final String GET_USR_ICON = "GetUserIcon";// 获取用户头像
	private static final String GET_ITEM_LIST_BY_KEY = "GetItemListByKey";// 获取用户头像
	private static final String GETITEMLISTBY_COORDINATES = "GetItemListByCoordinates";// 获取周边
	private static final String GET_MEIN_REVIEW = "GetMeinReview";// 获取活动评论
	private static final String ADD_MEIN_REVIEW = "AddMeinReview";// 评论
	private static final String GET_FAQ = "GetFAQ";// 获取问题列表
	private static final String GET_ONLINE_ADVICE_METHD = "GetMsgList";// 获取咨询列表
	private static final String NEW_MESSAGE = "NewMessage";// 发送咨询信息

	public ArrayList<Question> getFAQ() throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_FAQ);
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		ArrayList<Question> questionList = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {

					JSONArray dataArray = rootJsonObject
							.getJSONArray("Results");
					if (dataArray != null && dataArray.length() > 0) {
						questionList = new ArrayList<Question>();
						Question question = null;
						for (int i = 0; i < dataArray.length(); i++) {
							question = new Question();
							question.setQuestion(dataArray.getJSONObject(i)
									.getString("Problem"));
							question.setAnswer(dataArray.getJSONObject(i)
									.getString("Answer"));
							questionList.add(question);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return questionList;
	}

	public MainPage GetHomePage() throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_HOME_PAGE);
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		MainPage homeData = null;
		//ArrayList<Active> activeList = new ArrayList<Active>();
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					homeData = new MainPage();
					homeData.setNewsArray(new ArrayList<News>());
					JSONObject dataObject = rootJsonObject
							.getJSONObject("Results");
					JSONArray dataArray = dataObject.getJSONArray("imgList");
					News newsT = null;
					for (int i = 0; i < dataArray.length(); i++) {
						newsT = new News();
						newsT.setID(dataArray.getJSONObject(i).getString("NewsId"));
						newsT.setImg(BANNER_DOWNLOAD_URL+dataArray.getJSONObject(i).getString("Pic"));
						homeData.getNewsArray().add(newsT);
					}
					JSONArray dataArrayNewsList = dataObject
							.getJSONArray("newsList");
					homeData.setActiveArray(new ArrayList<Active>());
					if (dataArrayNewsList != null
							&& dataArrayNewsList.length() > 0) {
						Active active = null;
						for (int i = 0; i < dataArray.length(); i++) {
							active = new Active();
							active.setID(dataArrayNewsList.getJSONObject(i)
									.getString("id"));
						
							active.setTitle(dataArrayNewsList.getJSONObject(i)
									.getString("xmmc"));
							
							homeData.getActiveArray().add(active);
						}
						
					}
					return homeData;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	public String getUserIcon(String uId) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_USR_ICON);
		rpc.addProperty("uId", uId);
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		String imgUrl = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONObject dataObject = rootJsonObject
							.getJSONObject("Results");
					imgUrl = DOWNLOAD_URL + dataObject.getString("ImgUrl");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return imgUrl;

	}

	/**
	 * verify
	 * 
	 * @return 1 登录失败，0 登录成功
	 * */
	@Override
	public Account verify(String mobile, String password) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, LOG_IN);
		// 设置参数
		rpc.addProperty("userName", mobile);
		rpc.addProperty("psw", password);
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		Account account = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONObject dataObject = rootJsonObject
							.getJSONObject("Results");
					if (dataObject != null) {
						account = new Account();
						account.setUserID(dataObject.getString("id"));
						account.setNickName(dataObject.getString("PersonName"));
						account.setUserPW(dataObject.getString("PassWord"));
						account.setUserPhone(dataObject.getString("Mobile"));
						account.setIdNo(dataObject.getString("Identity_Card"));
						account.setCreatTime(dataObject.getString("InDateTime"));
						account.setUserImg(Constants.DEFAULT_PHOTO_URL);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return account;
	}

	public int addMein(String uId, String uName, String title, String txt,
			String image) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, ADDMEIN);
		// 设置参数
		rpc.addProperty("uId", uId);
		rpc.addProperty("uName", uName);
		rpc.addProperty("title", title);
		rpc.addProperty("txt", txt);
		rpc.addProperty("image", image);
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				return rootJsonObject.getInt("Success");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return 0;
	}

	public String updataUserIcon(String uId, String img) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, UPDATA_USERICON);
		// 设置参数
		rpc.addProperty("uId", uId);
		rpc.addProperty("img", img);
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if(rootJsonObject.getInt("Success") == 1)
					return DOWNLOAD_URL+rootJsonObject.getString("Results");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	/**
	 * 获取验证码
	 * 
	 * @return 1 失败，0 成功
	 **/
	public Integer CreateCheckCode(String mobile) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_CHECKCODE);
		// 设置参数
		rpc.addProperty("phoneNum", mobile);
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		try {
			return Integer.valueOf(new JSONObject(response)
					.getString("Success"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		}
	}

	/**
	 * 效验验证码是否正确
	 * 
	 * @return 1失败，0成功
	 * 
	 * **/
	public Integer CheckIsTrue(String mobile, String msgCode) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, CHECKCODE_ISTRUE);
		// 设置参数
		rpc.addProperty("msgCode", msgCode);
		rpc.addProperty("phoneNum", mobile);
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		try {
			return Integer.valueOf(new JSONObject(response)
					.getString("Success"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	public Boolean ForgetPwd(String msgCode, String phoneNum, String newPwd)
			throws WSError {
		// TODO Auto-generated method stub
		SoapObject rpc = new SoapObject(NAMESPACE, FINDPWD);
		// 设置参数
		rpc.addProperty("msgCode", msgCode);
		rpc.addProperty("phoneNum", phoneNum);
		rpc.addProperty("newPsw", newPwd);
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					return true;
				}
			} catch (JSONException e) {
				return false;
			}
		}
		return false;
	}

	public int Signin(String uId, String uName, double d, double f)
			throws WSError {

		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, SIGN_IN);
		// 设置参数
		rpc.addProperty("uId", uId);
		rpc.addProperty("uName", uName);
		rpc.addProperty("longitude", String.valueOf(d));
		rpc.addProperty("latitude", String.valueOf(f));
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				return rootJsonObject.getInt("Success");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return 0;

	}

	public Integer register(String phoneNum, String psw, String idCard,
			String userName) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, REGISTER);
		// 设置参数
		rpc.addProperty("phoneNum", phoneNum);
		rpc.addProperty("psw", psw);
		rpc.addProperty("idCard", idCard);
		rpc.addProperty("userName", userName);
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					return 0;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 1;
	}

	public int applyItem(String uId, String itemId) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, APPLY_ITEM);
		// 设置参数
		rpc.addProperty("uId", uId);
		rpc.addProperty("itemId", itemId);
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				return rootJsonObject.getInt("Success");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}

		}
		return 0;
	}

	public ArrayList<News> getNewsList(int pageIndex, int pageSize)
			throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_ALL_NEWS);
		// 设置参数
		rpc.addProperty("pageIndex", pageIndex);
		rpc.addProperty("pageSize", pageSize);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		ArrayList<News> newList = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONArray dataArray = rootJsonObject
							.getJSONArray("Results");
					if (dataArray != null && dataArray.length() > 0) {
						newList = new ArrayList<News>();
						News news = null;
						for (int i = 0; i < dataArray.length(); i++) {
							news = new News();
							news.setAuthor("活动动态");
							news.setCreatTime(dataArray.getJSONObject(i)
									.getString("dtpost").replace("T", " "));
							news.setDetails("");
							news.setID(dataArray.getJSONObject(i).getString(
									"id"));

							news.setTitle(dataArray.getJSONObject(i).getString(
									"title"));
							newList.add(news);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newList;
	}

	public News getDetailNews(String id) throws WSError {
		// TODO Auto-generated method stub
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_DETAIL_NEWS);
		rpc.addProperty("id", id);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		News news = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONObject dataObj = rootJsonObject
							.getJSONObject("Results");
					if (dataObj != null && dataObj.length() > 0) {
						news = new News();
						news.setAuthor("活动动态");
						news.setCreatTime(dataObj.getString("dtpost").replace("T", " "));
						news.setDetails(dataObj.getString("textHtml").replace("NewsImage.Aspx?", "http://www.volunteer.sh.cn/Website/NewsImage.Aspx?"));
						news.setID(dataObj.getString("id"));
						// news.setImg(dataObj.getString("img"));
						//news.setImg("http://qwcwebd.gservfocus.com/download/home1.jpg");
						news.setTitle(dataObj.getString("title"));
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return null;
			}
		}
		return news;
	}

	public ArrayList<Active> getActiveList(int pageIndex, int pageSize)
			throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_ALL_ACTIVE);
		rpc.addProperty("pageIndex", pageIndex);
		rpc.addProperty("pageSize", pageSize);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		ArrayList<Active> activeList = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONArray dataArray = rootJsonObject
							.getJSONArray("Results");
					if (dataArray != null && dataArray.length() > 0) {
						activeList = new ArrayList<Active>();
						Active active = null;
						for (int i = 0; i < dataArray.length(); i++) {
							active = new Active();
							active.setID(dataArray.getJSONObject(i).getString(
									"id"));
							active.setTitle(dataArray.getJSONObject(i)
									.getString("xmmc"));
							active.setUnit(dataArray.getJSONObject(i)
									.getString("ssdw"));
							active.setOrganize(dataArray.getJSONObject(i)
									.getString("szqx"));
							active.setDetails(dataArray.getJSONObject(i)
									.getString("xmnr"));
							active.setStatus(dataArray.getJSONObject(i)
									.getString("xmzt"));
							active.setAddress(dataArray.getJSONObject(i)
									.getString("xmdd"));
							active.setEMail(dataArray.getJSONObject(i)
									.getString("gbemail"));
							active.setTelephone(dataArray.getJSONObject(i)
									.getString("gblxdh"));
							active.setCreatTime(dataArray.getJSONObject(i)
									.getString("I_Date").replace("T", " "));
							active.setLatitude(dataArray.getJSONObject(i)
									.getDouble("lat"));
							active.setLongitude(dataArray.getJSONObject(i)
									.getDouble("lng"));
							activeList.add(active);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return activeList;

	}

	public ArrayList<Active> getItemListByKey(int pageIndex, int pageSize,
			String keyword) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_ITEM_LIST_BY_KEY);
		rpc.addProperty("pageIndex", pageIndex);
		rpc.addProperty("pageSize", pageSize);
		rpc.addProperty("keyword", keyword);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		ArrayList<Active> activeList = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONArray dataArray = rootJsonObject
							.getJSONArray("Results");
					if (dataArray != null && dataArray.length() > 0) {
						activeList = new ArrayList<Active>();
						Active active = null;
						for (int i = 0; i < dataArray.length(); i++) {
							active = new Active();
							active.setID(dataArray.getJSONObject(i).getString(
									"id"));
							active.setAddress(dataArray.getJSONObject(i)
									.getString("xmdd"));
							active.setDetails(dataArray.getJSONObject(i)
									.getString("xmnr"));
							active.setUnit(dataArray.getJSONObject(i)
									.getString("ssdw"));
							active.setOrganize(dataArray.getJSONObject(i)
									.getString("szqx"));
							/*
							 * active.setImg(dataArray.getJSONObject(i).getString
							 * ( "img"));
							 */
							active.setImg("http://qwcwebd.gservfocus.com/download/home1.jpg");

							active.setTitle(dataArray.getJSONObject(i)
									.getString("xmmc"));
							active.setCreatTime(dataArray.getJSONObject(i)
									.getString("I_Date").replace("T", " "));
							active.setLatitude(dataArray.getJSONObject(i)
									.getDouble("lat"));
							active.setLongitude(dataArray.getJSONObject(i)
									.getDouble("lng"));
							active.setStatus(dataArray.getJSONObject(i)
									.getString("xmzt"));
							activeList.add(active);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return activeList;

	}

	public ArrayList<Active> getMyActiveList(String uId, int pageIndex,
			int pageSize) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_ALL_MYACTIVE);
		rpc.addProperty("uId", uId);
		rpc.addProperty("pageIndex", pageIndex);
		rpc.addProperty("pageSize", pageSize);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		ArrayList<Active> activeList = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONArray dataArray = rootJsonObject
							.getJSONArray("Results");
					if (dataArray != null && dataArray.length() > 0) {
						activeList = new ArrayList<Active>();
						Active active = null;
						for (int i = 0; i < dataArray.length(); i++) {
							active = new Active();
							active.setID(dataArray.getJSONObject(i).getString(
									"id"));
							active.setAddress(dataArray.getJSONObject(i)
									.getString("xmdd"));
							active.setDetails(dataArray.getJSONObject(i)
									.getString("xmnr"));
							active.setUnit(dataArray.getJSONObject(i)
									.getString("ssdw"));
							active.setOrganize(dataArray.getJSONObject(i)
									.getString("szqx"));
							/*
							 * active.setImg(dataArray.getJSONObject(i).getString
							 * ( "img"));
							 */
							active.setImg("http://qwcwebd.gservfocus.com/download/home1.jpg");

							active.setTitle(dataArray.getJSONObject(i)
									.getString("xmmc"));
							active.setCreatTime(dataArray.getJSONObject(i)
									.getString("I_Date").replace("T", " "));
							active.setLatitude(dataArray.getJSONObject(i)
									.getDouble("lat"));
							active.setLongitude(dataArray.getJSONObject(i)
									.getDouble("lng"));
							active.setStatus(dataArray.getJSONObject(i)
									.getString("xmzt"));
							activeList.add(active);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return activeList;

	}

	public Active getDetailActive(String id) throws WSError {
		// TODO Auto-generated method stub
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_DETAIL_ACTIVE);
		rpc.addProperty("itemId", id);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		Active active = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONObject dataObj = rootJsonObject
							.getJSONObject("Results");
					if (dataObj != null && dataObj.length() > 0) {

						active = new Active();
						active.setID(dataObj.getString("id"));
						active.setAddress(dataObj.getString("xmdd"));
						active.setDetails(dataObj.getString("xmnr"));
						//active.setImg("http://qwcwebd.gservfocus.com/download/home1.jpg");

						active.setTitle(dataObj.getString("xmmc"));
						active.setCreatTime(dataObj.getString("I_Date").replace("T", " "));
						active.setLatitude(dataObj.getDouble("lat"));
						active.setLongitude(dataObj.getDouble("lng"));
						active.setStatus(dataObj.getString("xmzt"));
						active.setEMail(dataObj.getString("gbemail"));
						active.setTelephone(dataObj.getString("gblxdh"));
						active.setUnit(dataObj.getString("ssdw"));
						active.setOrganize(dataObj.getString("szqx"));
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return active;
	}

	public ArrayList<Display> getAllMeinList(int pageIndex, int pageSize,
			int type, int orderby) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_ALL_MEINLIST);
		rpc.addProperty("pageIndex", pageIndex);
		rpc.addProperty("pageSize", pageSize);
		rpc.addProperty("type", type);
		rpc.addProperty("orderby", orderby);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		ArrayList<Display> displayList = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONArray dataArray = rootJsonObject
							.getJSONArray("Results");
					if (dataArray != null && dataArray.length() > 0) {
						displayList = new ArrayList<Display>();
						Display display = null;
						for (int i = 0; i < dataArray.length(); i++) {
							display = new Display();
							display.setCreatTime(dataArray.getJSONObject(i)
									.getString("CreatTime"));
							display.setCriticismNum(dataArray.getJSONObject(i)
									.getInt("CriticismNum"));
							display.setDetails(dataArray.getJSONObject(i)
									.getString("Details"));
							display.setID(dataArray.getJSONObject(i).getInt(
									"ID"));
							display.setUID(dataArray.getJSONObject(i).getInt(
									"UID"));
							display.setReviewCount(dataArray.getJSONObject(i)
									.getInt("ReviewCount"));
							display.setPraiseNum(dataArray.getJSONObject(i)
									.getInt("PraiseNum"));
							display.setImageUrl(DOWNLOAD_URL
									+ dataArray.getJSONObject(i).getString(
											"ImageUrl"));
							display.setTitle(dataArray.getJSONObject(i)
									.getString("Title"));
							display.setUName(dataArray.getJSONObject(i)
									.getString("UName"));
							displayList.add(display);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return displayList;

	}

	public ArrayList<Display> getAllMyMeinList(String uId, int pageIndex,
			int pageSize) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_ALL_MYMEINLIST);
		rpc.addProperty("uId", uId);
		rpc.addProperty("pageIndex", pageIndex);
		rpc.addProperty("pageSize", pageSize);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		ArrayList<Display> displayList = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONArray dataArray = rootJsonObject
							.getJSONArray("Results");
					if (dataArray != null && dataArray.length() > 0) {
						displayList = new ArrayList<Display>();
						Display display = null;
						for (int i = 0; i < dataArray.length(); i++) {
							display = new Display();
							display.setCreatTime(dataArray.getJSONObject(i)
									.getString("CreatTime"));
							display.setCriticismNum(dataArray.getJSONObject(i)
									.getInt("CriticismNum"));
							display.setDetails(dataArray.getJSONObject(i)
									.getString("Details"));
							display.setID(dataArray.getJSONObject(i).getInt(
									"ID"));
							display.setUID(dataArray.getJSONObject(i).getInt(
									"UID"));
							display.setReviewCount(dataArray.getJSONObject(i)
									.getInt("ReviewCount"));
							display.setPraiseNum(dataArray.getJSONObject(i)
									.getInt("PraiseNum"));
							display.setImageUrl(DOWNLOAD_URL
									+ dataArray.getJSONObject(i).getString(
											"ImageUrl"));
							display.setTitle(dataArray.getJSONObject(i)
									.getString("Title"));
							display.setUName(dataArray.getJSONObject(i)
									.getString("UName"));
							displayList.add(display);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return displayList;

	}

	public Display getDetailDisplay(String mId) throws WSError {
		// TODO Auto-generated method stub
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_DETAIL_MEIN);
		rpc.addProperty("mId", mId);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		Display display = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONObject dataObj = rootJsonObject
							.getJSONObject("Results");
					if (dataObj != null && dataObj.length() > 0) {

						display = new Display();
						display.setCreatTime(dataObj.getString("CreatTime"));
						display.setCriticismNum(dataObj.getInt("CriticismNum"));
						display.setDetails(dataObj.getString("Details"));
						display.setID(dataObj.getInt("ID"));
						display.setUID(dataObj.getInt("UID"));
						display.setReviewCount(dataObj.getInt("ReviewCount"));
						display.setPraiseNum(dataObj.getInt("PraiseNum"));
						display.setImageUrl(DOWNLOAD_URL
								+ dataObj.getString("ImageUrl"));
						display.setTitle(dataObj.getString("Title"));
						display.setUName(dataObj.getString("UName"));
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return display;
	}

	public int PraiseMein(String mId, String userId, int isPraise)
			throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, PRAISE_MEIN);
		// 设置参数
		rpc.addProperty("mId", mId);
		rpc.addProperty("userId", userId);
		rpc.addProperty("isPraise", isPraise);
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				return rootJsonObject.getInt("Success");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return 0;
	}

	public ArrayList<Integer> GetSignInList(String uId, String date)
			throws WSError {

		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_SIGN_INLIST);
		rpc.addProperty("uId", uId);
		rpc.addProperty("date", date);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		ArrayList<Integer> intArray = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONArray dataArray = rootJsonObject
							.getJSONArray("Results");
					if (dataArray != null && dataArray.length() > 0) {
						intArray = new ArrayList<Integer>();
						for (int i = 0; i < dataArray.length(); i++) {
							String a[] = dataArray.getJSONObject(i)
									.getString("CreatTime").split("T");
							String b[] = a[0].split("-");
							int c = Integer.parseInt(b[2]);
							intArray.add(c);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return intArray;
	}

	public ArrayList<Active> getItemListByCoordinates(int pageIndex,
			int pageSize, String lng, String lat) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GETITEMLISTBY_COORDINATES);
		rpc.addProperty("pageIndex", pageIndex);
		rpc.addProperty("pageSize", pageSize);
		rpc.addProperty("lng", lng);
		rpc.addProperty("lat", lat);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		ArrayList<Active> activeList = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONArray dataArray = rootJsonObject
							.getJSONArray("Results");
					if (dataArray != null && dataArray.length() > 0) {
						activeList = new ArrayList<Active>();
						Active active = null;
						for (int i = 0; i < dataArray.length(); i++) {
							active = new Active();
							active.setID(dataArray.getJSONObject(i).getString(
									"id"));
							active.setAddress(dataArray.getJSONObject(i)
									.getString("xmdd"));
							active.setDetails(dataArray.getJSONObject(i)
									.getString("xmnr"));
							/*
							 * active.setImg(dataArray.getJSONObject(i).getString
							 * ( "img"));
							 */
							//active.setImg("http://qwcwebd.gservfocus.com/download/home1.jpg");

							active.setTitle(dataArray.getJSONObject(i)
									.getString("xmmc"));
							active.setCreatTime(dataArray.getJSONObject(i)
									.getString("I_Date"));
							active.setLatitude(Double.valueOf(dataArray.getJSONObject(i)
									.getString("lat")));
							active.setLongitude(Double.valueOf(dataArray.getJSONObject(i)
									.getString("lng")));
							active.setStatus(dataArray.getJSONObject(i)
									.getString("xmzt"));
							activeList.add(active);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return activeList;
	}

	public ArrayList<MeinCommend> getMeinReview(String mId) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_MEIN_REVIEW);
		rpc.addProperty("mId", mId);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		ArrayList<MeinCommend> commendList = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONArray dataArray = rootJsonObject
							.getJSONArray("Results");
					if (dataArray != null && dataArray.length() > 0) {
						commendList = new ArrayList<MeinCommend>();
						MeinCommend meincommend = null;
						for (int i = 0; i < dataArray.length(); i++) {
							meincommend = new MeinCommend();
							meincommend.setID(dataArray.getJSONObject(i)
									.getString("ID"));
							meincommend.setCreatTime(dataArray.getJSONObject(i)
									.getString("CreatTime").replace("T", "-"));
							meincommend.setReview(dataArray.getJSONObject(i)
									.getString("Review"));
							meincommend.setUName(dataArray.getJSONObject(i)
									.getString("UName"));
							meincommend.setUserIcon(DOWNLOAD_URL
									+ dataArray.getJSONObject(i).getString(
											"UserIcon"));
							commendList.add(meincommend);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return commendList;
	}

	public int addMeinReview(String mId, String uId, String uName, String txt)
			throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, ADD_MEIN_REVIEW);
		// 设置参数
		rpc.addProperty("mId", mId);
		rpc.addProperty("uId", uId);
		rpc.addProperty("uName", uName);
		rpc.addProperty("txt", txt);
		// 获取响应值
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				return rootJsonObject.getInt("Success");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 获取留言信息
	 * 
	 * @return ArrayList<OnlineAdvice>
	 * **/
	public ArrayList<OnlineAdvice> getOnlieAdviceList(int pageIndex,
			int pageSize, String uid) throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, GET_ONLINE_ADVICE_METHD);
		rpc.addProperty("pageIndex", pageIndex);
		rpc.addProperty("pageSize", pageSize);
		rpc.addProperty("uId", uid);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		ArrayList<OnlineAdvice> adviceList = null;
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				if (rootJsonObject.getInt("Success") == 1) {
					JSONArray dataArray = rootJsonObject
							.getJSONArray("Results");
					if (dataArray != null && dataArray.length() > 0) {
						adviceList = new ArrayList<OnlineAdvice>();
						OnlineAdvice advice = null;
						for (int i = 0; i < dataArray.length(); i++) {
							advice = new OnlineAdvice();
							advice.setID(dataArray.getJSONObject(i).getString(
									"ID"));
							advice.setF(dataArray.getJSONObject(i).getString(
									"F"));
							advice.setQ(dataArray.getJSONObject(i).getString(
									"Q"));
							advice.setCreateTime(dataArray.getJSONObject(i)
									.getString("CreatTime"));
							advice.setMark(dataArray.getJSONObject(i).getInt(
									"Mark"));
							advice.setAdviceUrl(Constants.DEFAULT_PHOTO_URL);
							advice.setuUrl(Constants.mAccount.getUserImg());

							adviceList.add(advice);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return adviceList;
	}

	public int sendNewAdvice(String uId, String uName, String question)
			throws WSError {
		// 指定 WebService 的命名空间和调用方法
		SoapObject rpc = new SoapObject(NAMESPACE, NEW_MESSAGE);
		rpc.addProperty("uId", uId);
		rpc.addProperty("uName", uName);
		rpc.addProperty("question", question);
		String response = WebServicesUtil.doWebServicesRequest(rpc, USER_URL);
		if (!TextUtils.isEmpty(response)) {
			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(response);
				return rootJsonObject.getInt("Success");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
}