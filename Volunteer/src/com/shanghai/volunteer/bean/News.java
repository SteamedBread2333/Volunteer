package com.shanghai.volunteer.bean;

import java.io.Serializable;

public class News implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID;
	private String Title;
	private String Img;
	private String Author;// 作者
	private String CreatTime;// 创建时间
	private String Details;

	
	
	public News() {
		super();
	}

	public News(String title, String img, String author, String details) {
		super();
		Title = title;
		Img = img;
		Author = author;
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

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getCreatTime() {
		return CreatTime;
	}

	public void setCreatTime(String creatTime) {
		CreatTime = creatTime;
	}

	public String getDetails() {
		return Details;
	}

	public void setDetails(String details) {
		Details = details;
	}

}
