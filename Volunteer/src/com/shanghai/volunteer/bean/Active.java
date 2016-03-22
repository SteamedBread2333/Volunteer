package com.shanghai.volunteer.bean;

import java.io.Serializable;

public class Active implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID;// 活动ID
	private String Title;// 标题
	private String Img;
	private String CreatTime;
	private String Unit;
	private String Organize;
	private String Telephone;
	private String EMail;
	private String Address;
	private double Longitude;// 经度
	private double Latitude;// 纬度
	private String Details;
	private String Condition;// 参与条件
	private String Status;// 状态

	public Active() {
		super();
	}

	public Active(String title, String img, String address, String details,
			String status) {
		super();
		Title = title;
		Img = img;
		Address = address;
		Details = details;
		Status = status;
	}

	public Active(String title, String img, String address, String details) {
		super();
		Title = title;
		Img = img;
		Address = address;
		Details = details;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getImg() {
		return Img;
	}

	public void setImg(String img) {
		Img = img;
	}

	public String getCreatTime() {
		return CreatTime;
	}

	public void setCreatTime(String creatTime) {
		CreatTime = creatTime;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public String getOrganize() {
		return Organize;
	}

	public void setOrganize(String organize) {
		Organize = organize;
	}

	public String getTelephone() {
		return Telephone;
	}

	public void setTelephone(String telephone) {
		Telephone = telephone;
	}

	public String getEMail() {
		return EMail;
	}

	public void setEMail(String eMail) {
		EMail = eMail;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public String getDetails() {
		return Details;
	}

	public void setDetails(String details) {
		Details = details;
	}

	public String getCondition() {
		return Condition;
	}

	public void setCondition(String condition) {
		Condition = condition;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
}
