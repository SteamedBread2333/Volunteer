package com.shanghai.volunteer.bean;

public class ArroundLocation {

	private double latitude;
	private double longitude;
	private String activeTitle;
	private String Title;
	private String Url;

	public ArroundLocation(double latitude, double longitude,
			String activeTitle, String title, String url) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.activeTitle = activeTitle;
		Title = title;
		Url = url;
	}

	public ArroundLocation() {
		super();
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getActiveTitle() {
		return activeTitle;
	}

	public void setActiveTitle(String activeTitle) {
		this.activeTitle = activeTitle;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

}
