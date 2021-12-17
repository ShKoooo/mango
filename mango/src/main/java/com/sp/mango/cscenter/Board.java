package com.sp.mango.cscenter;

import org.springframework.web.multipart.MultipartFile;

public class Board {
	private int listNum;
	
	private int noticeNum;
	private String userId;
	private String userName;
	private String nSubject;
	private String nContent;
	private String nRegDate;
	
	private String saveFilename;
	private String originalFilename;
	private MultipartFile selectFile;
	
	private long gap;
	

	public int getListNum() {
		return listNum;
	}

	public void setListNum(int listNum) {
		this.listNum = listNum;
	}

	public int getNoticeNum() {
		return noticeNum;
	}

	public void setNoticeNum(int noticeNum) {
		this.noticeNum = noticeNum;
	}

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

	public String getnSubject() {
		return nSubject;
	}

	public void setnSubject(String nSubject) {
		this.nSubject = nSubject;
	}

	public String getnContent() {
		return nContent;
	}

	public void setnContent(String nContent) {
		this.nContent = nContent;
	}

	public String getnRegDate() {
		return nRegDate;
	}

	public void setnRegDate(String nRegDate) {
		this.nRegDate = nRegDate;
	}

	public String getSaveFilename() {
		return saveFilename;
	}

	public void setSaveFilename(String saveFilename) {
		this.saveFilename = saveFilename;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public MultipartFile getSelectFile() {
		return selectFile;
	}

	public void setSelectFile(MultipartFile selectFile) {
		this.selectFile = selectFile;
	}

	public long getGap() {
		return gap;
	}

	public void setGap(long gap) {
		this.gap = gap;
	}
	
	
	
	

	

}
