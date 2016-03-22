package com.shanghai.volunteer.bean;

public class MeinCommend {

	private String ID;
	private String UName;
	private String Review;
	private String CreatTime;
	private String UserIcon;

	public MeinCommend() {
		super();
	}

	public MeinCommend(String uName, String review, String creatTime,
			String userIcon) {
		super();
		UName = uName;
		Review = review;
		CreatTime = creatTime;
		UserIcon = userIcon;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getUName() {
		return UName;
	}

	public void setUName(String uName) {
		UName = uName;
	}

	public String getReview() {
		return Review;
	}

	public void setReview(String review) {
		Review = review;
	}

	public String getCreatTime() {
		return CreatTime;
	}

	public void setCreatTime(String creatTime) {
		CreatTime = creatTime;
	}

	public String getUserIcon() {
		return UserIcon;
	}

	public void setUserIcon(String userIcon) {
		UserIcon = userIcon;
	}

}
