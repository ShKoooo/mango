package com.sp.mango.admin.product;

public class ProductReport {
	private int reportNum;
	private int num;
	private int catNum;
	private String reasonName;
	private String reportContent;
	private String reportRegDate;
	private String reportChecked;
	private String subject;
	private String fullSubject;
	
	public int getReportNum() {
		return reportNum;
	}
	public void setReportNum(int reportNum) {
		this.reportNum = reportNum;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getCatNum() {
		return catNum;
	}
	public void setCatNum(int catNum) {
		this.catNum = catNum;
	}
	public String getReasonName() {
		return reasonName;
	}
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	public String getReportContent() {
		return reportContent;
	}
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	public String getReportRegDate() {
		return reportRegDate;
	}
	public void setReportRegDate(String reportRegDate) {
		this.reportRegDate = reportRegDate;
	}
	public String getReportChecked() {
		return reportChecked;
	}
	public void setReportChecked(String reportChecked) {
		this.reportChecked = reportChecked;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFullSubject() {
		return fullSubject;
	}
	public void setFullSubject(String fullSubject) {
		this.fullSubject = fullSubject;
	}
}
