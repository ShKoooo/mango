package com.sp.mango.mypage;

public class Note {
	private int noteNum;
	private String userNickName;
	private String sendId;
	private String receiveId;
	private String noteContent;
	private String noteRegDate;
	private String noteReadDate;
	private String isSendDelete;
	private String isRecDelete;
	private double recentDPlus;
	
	private String meId;
	private String youId;
	private String meNick;
	private String youNick;
	private String timeMsg;
	
	private int notReadCount;
	
	public int getNoteNum() {
		return noteNum;
	}
	public void setNoteNum(int noteNum) {
		this.noteNum = noteNum;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public String getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	public String getNoteRegDate() {
		return noteRegDate;
	}
	public void setNoteRegDate(String noteRegDate) {
		this.noteRegDate = noteRegDate;
	}
	public String getNoteReadDate() {
		return noteReadDate;
	}
	public void setNoteReadDate(String noteReadDate) {
		this.noteReadDate = noteReadDate;
	}
	public String getIsSendDelete() {
		return isSendDelete;
	}
	public void setIsSendDelete(String isSendDelete) {
		this.isSendDelete = isSendDelete;
	}
	public String getIsRecDelete() {
		return isRecDelete;
	}
	public void setIsRecDelete(String isRecDelete) {
		this.isRecDelete = isRecDelete;
	}
	public double getRecentDPlus() {
		return recentDPlus;
	}
	public void setRecentDPlus(double recentDPlus) {
		this.recentDPlus = recentDPlus;
	}
	public String getMeId() {
		return meId;
	}
	public void setMeId(String meId) {
		this.meId = meId;
	}
	public String getYouId() {
		return youId;
	}
	public void setYouId(String youId) {
		this.youId = youId;
	}
	public String getMeNick() {
		return meNick;
	}
	public void setMeNick(String meNick) {
		this.meNick = meNick;
	}
	public String getYouNick() {
		return youNick;
	}
	public void setYouNick(String youNick) {
		this.youNick = youNick;
	}
	public String getTimeMsg() {
		return timeMsg;
	}
	public void setTimeMsg(String timeMsg) {
		this.timeMsg = timeMsg;
	}
	
	public int getNotReadCount() {
		return notReadCount;
	}
	public void setNotReadCount(int notReadCount) {
		this.notReadCount = notReadCount;
	}
}
