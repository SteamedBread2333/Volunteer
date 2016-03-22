package com.shanghai.volunteer.bean;

public class OnlineAdvice {
	private String ID;
	private String Q;
	private String F;
	private int mark;
	private String createTime;
	private String adviceUrl;
	private String uUrl;

	public OnlineAdvice() {
		super();
	}

	public OnlineAdvice(String q, String uUrl) {
		super();
		Q = q;
		this.uUrl = uUrl;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getQ() {
		return Q;
	}

	public void setQ(String q) {
		Q = q;
	}

	public String getF() {
		return F;
	}

	public void setF(String f) {
		F = f;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAdviceUrl() {
		return adviceUrl;
	}

	public void setAdviceUrl(String adviceUrl) {
		this.adviceUrl = adviceUrl;
	}

	public String getuUrl() {
		return uUrl;
	}

	public void setuUrl(String uUrl) {
		this.uUrl = uUrl;
	}

}
