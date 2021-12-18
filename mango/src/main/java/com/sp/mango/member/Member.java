package com.sp.mango.member;

import org.springframework.web.multipart.MultipartFile;

public class Member {
	private String userId;
	private String userPwd;
	private String userName;
	private String userNickName;
	private String regDate;
	private String update_Date;
	private String nickUpdate_Date;
	private String userTel;
	private String userEmail;
	private int memberEnable;
	private int loginFail;
	private int membershipNum;
	private String userImgSaveFileName;
	private String userImgOrigFileName;
	private MultipartFile profileImg;
	
	private String birth;
	
	private String email1;
	private String email2;
	private String tel1;
	private String tel2;
	private String tel3;

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
	public void setUserTel(String tel1, String tel2, String tel3) {
		this.userTel = tel1+"-"+tel2+"-"+tel3;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public void setUserEmail(String email1, String email2) {
		this.userEmail = email1+"@"+email2;
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
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getTel3() {
		return tel3;
	}
	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}
}
