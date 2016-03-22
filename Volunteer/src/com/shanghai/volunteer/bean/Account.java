package com.shanghai.volunteer.bean;

public class Account {

	private String UserID;
	private String UserPhone;
	private String UserPW;
	private String NickName;
	private String UserImg;
	private String UserEmail;
	private String RealName;
	private int DType;// 证件类型
	private String IdNo;// 证件号
	private String Address;
	private String Signature;//个人说明
	private String CreatTime;

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUserPhone() {
		return UserPhone;
	}

	public void setUserPhone(String userPhone) {
		UserPhone = userPhone;
	}

	public String getUserPW() {
		return UserPW;
	}

	public void setUserPW(String userPW) {
		UserPW = userPW;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getUserImg() {
		return UserImg;
	}

	public void setUserImg(String userImg) {
		UserImg = userImg;
	}

	public String getUserEmail() {
		return UserEmail;
	}

	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}

	public String getRealName() {
		return RealName;
	}

	public void setRealName(String realName) {
		RealName = realName;
	}

	public int getDType() {
		return DType;
	}

	public void setDType(int dType) {
		DType = dType;
	}

	public String getIdNo() {
		return IdNo;
	}

	public void setIdNo(String idNo) {
		IdNo = idNo;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getSignature() {
		return Signature;
	}

	public void setSignature(String signature) {
		Signature = signature;
	}

	public String getCreatTime() {
		return CreatTime;
	}

	public void setCreatTime(String creatTime) {
		CreatTime = creatTime;
	}

}
