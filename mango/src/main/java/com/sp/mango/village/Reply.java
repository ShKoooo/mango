package com.sp.mango.village;

public class Reply {
	private int vreplyNum;
	private int vNum;
	private String userId;
	private String userNickName;
	private String vrContent;
	private String vrRegDate;
	private int vrAnswer;
	
	private int answerCount;
	private int likeCount;
	private int disLikeCount;
	
	public int getVreplyNum() {
		return vreplyNum;
	}
	public void setVreplyNum(int vreplyNum) {
		this.vreplyNum = vreplyNum;
	}
	public int getvNum() {
		return vNum;
	}
	public void setvNum(int vNum) {
		this.vNum = vNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getVrContent() {
		return vrContent;
	}
	public void setVrContent(String vrContent) {
		this.vrContent = vrContent;
	}
	public String getVrRegDate() {
		return vrRegDate;
	}
	public void setVrRegDate(String vrRegDate) {
		this.vrRegDate = vrRegDate;
	}
	public int getVrAnswer() {
		return vrAnswer;
	}
	public void setVrAnswer(int vrAnswer) {
		this.vrAnswer = vrAnswer;
	}
	public int getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getDisLikeCount() {
		return disLikeCount;
	}
	public void setDisLikeCount(int disLikeCount) {
		this.disLikeCount = disLikeCount;
	}
	
	
}
