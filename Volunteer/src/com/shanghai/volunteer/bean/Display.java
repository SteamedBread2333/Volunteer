package com.shanghai.volunteer.bean;

public class Display {

	private int ID;
	private int UID;
	private String UName;
	private String Title;
	private String ImageUrl;
	private String Details;
	private int PraiseNum;
	private int CriticismNum;
	private int ReviewCount;
	private String CreatTime;

	public int getReviewCount() {
		return ReviewCount;
	}

	public void setReviewCount(int reviewCount) {
		ReviewCount = reviewCount;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int uID) {
		UID = uID;
	}

	public String getUName() {
		return UName;
	}

	public void setUName(String uName) {
		UName = uName;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

	public String getDetails() {
		return Details;
	}

	public void setDetails(String details) {
		Details = details;
	}

	public int getPraiseNum() {
		return PraiseNum;
	}

	public void setPraiseNum(int praiseNum) {
		PraiseNum = praiseNum;
	}

	public int getCriticismNum() {
		return CriticismNum;
	}

	public void setCriticismNum(int criticismNum) {
		CriticismNum = criticismNum;
	}

	public String getCreatTime() {
		return CreatTime;
	}

	public void setCreatTime(String creatTime) {
		CreatTime = creatTime;
	}

}
