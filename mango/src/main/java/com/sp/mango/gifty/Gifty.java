package com.sp.mango.gifty;

import org.springframework.web.multipart.MultipartFile;

public class Gifty {
	private int gNum;
	private String userId;
	private String userNickName;
	private String gSubject;
	private String gRegdate;
	private String gExpdate;
	private int gHitCount;
	private int gPrice;
	private String gContent;
	private String gUpdate_date;
	private String gStatus;
	private String gSoldDate;
	private int gcNum;
	private String gcName;
	
	private String gIsProposable;
	private int gPropPrice;
	
	private int giftyWishNum;
	private String gwRegDate;
	private int gWishCount;
	
	private int gImgNum;
	private String gImgSaveFileName;
	private MultipartFile selectFile;
	
	private double manner;
	
	private int gUp; // 끌어올리는 횟수
	
	private String userImgSaveFileName; // 프로필 사진
	
	private String sendId; // 판매자에게 쪽지 보낸 사람 아이디
	private String noteRegDate; // 쪽지 보낸 날짜
	private String isSendDelete; // 보낸 사람 삭제 여부
	
	private int myAcNum;
	private String target_id;
	private int income;
	private int expenses;
	
	private String bookAble; // 예약받기

	
	public int getgNum() {
		return gNum;
	}
	public void setgNum(int gNum) {
		this.gNum = gNum;
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
	public String getgSubject() {
		return gSubject;
	}
	public void setgSubject(String gSubject) {
		this.gSubject = gSubject;
	}
	public String getgRegdate() {
		return gRegdate;
	}
	public void setgRegdate(String gRegdate) {
		this.gRegdate = gRegdate;
	}
	public String getgExpdate() {
		return gExpdate;
	}
	public void setgExpdate(String gExpdate) {
		this.gExpdate = gExpdate;
	}
	public int getgHitCount() {
		return gHitCount;
	}
	public void setgHitCount(int gHitCount) {
		this.gHitCount = gHitCount;
	}
	public int getgPrice() {
		return gPrice;
	}
	public void setgPrice(int gPrice) {
		this.gPrice = gPrice;
	}
	public String getgContent() {
		return gContent;
	}
	public void setgContent(String gContent) {
		this.gContent = gContent;
	}
	public String getgUpdate_date() {
		return gUpdate_date;
	}
	public void setgUpdate_date(String gUpdate_date) {
		this.gUpdate_date = gUpdate_date;
	}
	public String getgStatus() {
		return gStatus;
	}
	public void setgStatus(String gStatus) {
		this.gStatus = gStatus;
	}
	public String getgSoldDate() {
		return gSoldDate;
	}
	public void setgSoldDate(String gSoldDate) {
		this.gSoldDate = gSoldDate;
	}
	public int getGcNum() {
		return gcNum;
	}
	public void setGcNum(int gcNum) {
		this.gcNum = gcNum;
	}
	public String getGcName() {
		return gcName;
	}
	public void setGcName(String gcName) {
		this.gcName = gcName;
	}
	public int getgImgNum() {
		return gImgNum;
	}
	public void setgImgNum(int gImgNum) {
		this.gImgNum = gImgNum;
	}
	public String getgImgSaveFileName() {
		return gImgSaveFileName;
	}
	public void setgImgSaveFileName(String gImgSaveFileName) {
		this.gImgSaveFileName = gImgSaveFileName;
	}
	public MultipartFile getSelectFile() {
		return selectFile;
	}
	public void setSelectFile(MultipartFile selectFile) {
		this.selectFile = selectFile;
	}
	public String getgIsProposable() {
		return gIsProposable;
	}
	public void setgIsProposable(String gIsProposable) {
		this.gIsProposable = gIsProposable;
	}
	public int getgPropPrice() {
		return gPropPrice;
	}
	public void setgPropPrice(int gPropPrice) {
		this.gPropPrice = gPropPrice;
	}
	public int getGiftyWishNum() {
		return giftyWishNum;
	}
	public void setGiftyWishNum(int giftyWishNum) {
		this.giftyWishNum = giftyWishNum;
	}
	public String getGwRegDate() {
		return gwRegDate;
	}
	public void setGwRegDate(String gwRegDate) {
		this.gwRegDate = gwRegDate;
	}
	public int getgWishCount() {
		return gWishCount;
	}
	public void setgWishCount(int gWishCount) {
		this.gWishCount = gWishCount;
	}
	public double getManner() {
		return manner;
	}
	public void setManner(double manner) {
		this.manner = manner;
	}
	public int getgUp() {
		return gUp;
	}
	public void setgUp(int gUp) {
		this.gUp = gUp;
	}
	public String getUserImgSaveFileName() {
		return userImgSaveFileName;
	}
	public void setUserImgSaveFileName(String userImgSaveFileName) {
		this.userImgSaveFileName = userImgSaveFileName;
	}
	
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public String getNoteRegDate() {
		return noteRegDate;
	}
	public void setNoteRegDate(String noteRegDate) {
		this.noteRegDate = noteRegDate;
	}
	public String getIsSendDelete() {
		return isSendDelete;
	}
	public void setIsSendDelete(String isSendDelete) {
		this.isSendDelete = isSendDelete;
	}
	public int getMyAcNum() {
		return myAcNum;
	}
	public void setMyAcNum(int myAcNum) {
		this.myAcNum = myAcNum;
	}
	public String getTarget_id() {
		return target_id;
	}
	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}
	public int getIncome() {
		return income;
	}
	public void setIncome(int income) {
		this.income = income;
	}
	public int getExpenses() {
		return expenses;
	}
	public void setExpenses(int expenses) {
		this.expenses = expenses;
	}
	public String getBookAble() {
		return bookAble;
	}
	public void setBookAble(String bookAble) {
		this.bookAble = bookAble;
	}
}
