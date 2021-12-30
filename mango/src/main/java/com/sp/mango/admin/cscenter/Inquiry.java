package com.sp.mango.admin.cscenter;

import org.springframework.web.multipart.MultipartFile;

public class Inquiry {
	private String userId;
	private int listNum;
	private int state;
	
	private int inquiryNum;
	private String inquiryEmail;
	private String inquiryContent;
	private String inquiryRegDate;

	private String saveFilename;
	private String originalFilename;
	private MultipartFile selectFile;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getInquiryNum() {
		return inquiryNum;
	}
	public void setInquiryNum(int inquiryNum) {
		this.inquiryNum = inquiryNum;
	}
	public String getInquiryEmail() {
		return inquiryEmail;
	}
	public void setInquiryEmail(String inquiryEmail) {
		this.inquiryEmail = inquiryEmail;
	}
	public String getInquiryContent() {
		return inquiryContent;
	}
	public void setInquiryContent(String inquiryContent) {
		this.inquiryContent = inquiryContent;
	}
	public String getInquiryRegDate() {
		return inquiryRegDate;
	}
	public void setInquiryRegDate(String inquiryRegDate) {
		this.inquiryRegDate = inquiryRegDate;
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
	
	
	
	
	
	
}
