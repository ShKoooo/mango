package com.sp.mango.member;

import org.springframework.web.multipart.MultipartFile;

public class Member {
	private String userId;
	private String userPwd;
	private String userName;
	private String userNickName;
	private String regDate;
	private String update_Date;
	private String userTel;
	private String userEmail;
	private int memberEnable;
	private int loginFail;
	private int membershipNum;
	private String userImgSaveFileName;
	private String userImgOrigFileName;
	private MultipartFile userImgFile;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getUpdate_Date() {
		return update_Date;
	}
	public void setUpdate_Date(String update_Date) {
		this.update_Date = update_Date;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public int getMemberEnable() {
		return memberEnable;
	}
	public void setMemberEnable(int memberEnable) {
		this.memberEnable = memberEnable;
	}
	public int getLoginFail() {
		return loginFail;
	}
	public void setLoginFail(int loginFail) {
		this.loginFail = loginFail;
	}
	public int getMembershipNum() {
		return membershipNum;
	}
	public void setMembershipNum(int membershipNum) {
		this.membershipNum = membershipNum;
	}
	public String getUserImgSaveFileName() {
		return userImgSaveFileName;
	}
	public void setUserImgSaveFileName(String userImgSaveFileName) {
		this.userImgSaveFileName = userImgSaveFileName;
	}
	public String getUserImgOrigFileName() {
		return userImgOrigFileName;
	}
	public void setUserImgOrigFileName(String userImgOrigFileName) {
		this.userImgOrigFileName = userImgOrigFileName;
	}
	public MultipartFile getUserImgFile() {
		return userImgFile;
	}
	public void setUserImgFile(MultipartFile userImgFile) {
		this.userImgFile = userImgFile;
	}
}
