package com.sp.mango.admin.member;

import org.springframework.web.multipart.MultipartFile;

public class Member {
	private String userId;
	private String userName;
	private String userNickName;
	private String regDate;
	private String fullRegDate;
	private String update_date;
	private String nickUpdate_Date;
	private String userTel;
	private String userEmail;
	private int memberEnable;
	private int loginFail;
	private int membershipNum;
	private String membershipName;
	private String userImgSaveFileName;
	private MultipartFile profileImg;
	private String birth;
	private String business;
	private String busNickName;
	private int cntRep;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getFullRegDate() {
		return fullRegDate;
	}
	public void setFullRegDate(String fullRegDate) {
		this.fullRegDate = fullRegDate;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getNickUpdate_Date() {
		return nickUpdate_Date;
	}
	public void setNickUpdate_Date(String nickUpdate_Date) {
		this.nickUpdate_Date = nickUpdate_Date;
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
	public String getMembershipName() {
		return membershipName;
	}
	public void setMembershipName(String membershipName) {
		this.membershipName = membershipName;
	}
	public String getUserImgSaveFileName() {
		return userImgSaveFileName;
	}
	public void setUserImgSaveFileName(String userImgSaveFileName) {
		this.userImgSaveFileName = userImgSaveFileName;
	}
	public MultipartFile getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(MultipartFile profileImg) {
		this.profileImg = profileImg;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getBusNickName() {
		return busNickName;
	}
	public void setBusNickName(String busNickName) {
		this.busNickName = busNickName;
	}
	public int getCntRep() {
		return cntRep;
	}
	public void setCntRep(int cntRep) {
		this.cntRep = cntRep;
	}
}
