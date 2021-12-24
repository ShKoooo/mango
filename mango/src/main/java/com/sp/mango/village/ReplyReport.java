package com.sp.mango.village;

public class ReplyReport {
	private int vrReportNum; // 리플 신고번호
	private int vreplyNum; // 리플 번호
	private int vrrReasonNum; // 신고 사유 번호
	private String vrrReasonName; // 신고 사유 항목
	private String userId; // 신고자 아이디
	private String vrReportContent; // 신고 사유
	private String vrReportRegDate; // 신고 날짜
	private String vrReportChecked; // 신고 해결 여부

	public int getVrReportNum() {
		return vrReportNum;
	}
	public void setVrReportNum(int vrReportNum) {
		this.vrReportNum = vrReportNum;
	}
	public int getVreplyNum() {
		return vreplyNum;
	}
	public void setVreplyNum(int vreplyNum) {
		this.vreplyNum = vreplyNum;
	}
	public int getVrrReasonNum() {
		return vrrReasonNum;
	}
	public void setVrrReasonNum(int vrrReasonNum) {
		this.vrrReasonNum = vrrReasonNum;
	}
	public String getVrrReasonName() {
		return vrrReasonName;
	}
	public void setVrrReasonName(String vrrReasonName) {
		this.vrrReasonName = vrrReasonName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVrReportContent() {
		return vrReportContent;
	}
	public void setVrReportContent(String vrReportContent) {
		this.vrReportContent = vrReportContent;
	}
	public String getVrReportRegDate() {
		return vrReportRegDate;
	}
	public void setVrReportRegDate(String vrReportRegDate) {
		this.vrReportRegDate = vrReportRegDate;
	}
	public String getVrReportChecked() {
		return vrReportChecked;
	}
	public void setVrReportChecked(String vrReportChecked) {
		this.vrReportChecked = vrReportChecked;
	}
	
	
}
