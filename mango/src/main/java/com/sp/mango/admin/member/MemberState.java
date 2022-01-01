package com.sp.mango.admin.member;

public class MemberState {
	private int msNum;
	private String memo;
	private String regDate;
	private String userId;
	private int msCodeNum;
	private String msCodeName;
	
	public int getMsNum() {
		return msNum;
	}
	public void setMsNum(int msNum) {
		this.msNum = msNum;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getMsCodeNum() {
		return msCodeNum;
	}
	public void setMsCodeNum(int msCodeNum) {
		this.msCodeNum = msCodeNum;
	}
	public String getMsCodeName() {
		return msCodeName;
	}
	public void setMsCodeName(String msCodeName) {
		this.msCodeName = msCodeName;
	}
}
