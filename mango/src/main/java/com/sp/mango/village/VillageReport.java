package com.sp.mango.village;

public class VillageReport {
	private int vbbsRepNum; // 신고번호
	private int vNum; // 게시글 번호
	private int vrReasonNum; // 신고사유 번호
	private String vrReasonName; // 신고사유 항목 이름
	private String userId; // 신고자 아이디
	private String vbbsRepContent; // 신고 사유 상세
	private String vbbsRepRegDate; // 신고 날짜
	private String vbbsRepChecked; // 신고해결여부 True/False

	public int getVbbsRepNum() {
		return vbbsRepNum;
	}
	public void setVbbsRepNum(int vbbsRepNum) {
		this.vbbsRepNum = vbbsRepNum;
	}
	public int getvNum() {
		return vNum;
	}
	public void setvNum(int vNum) {
		this.vNum = vNum;
	}
	public int getVrReasonNum() {
		return vrReasonNum;
	}
	public void setVrReasonNum(int vrReasonNum) {
		this.vrReasonNum = vrReasonNum;
	}
	public String getVrReasonName() {
		return vrReasonName;
	}
	public void setVrReasonName(String vrReasonName) {
		this.vrReasonName = vrReasonName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVbbsRepContent() {
		return vbbsRepContent;
	}
	public void setVbbsRepContent(String vbbsRepContent) {
		this.vbbsRepContent = vbbsRepContent;
	}
	public String getVbbsRepRegDate() {
		return vbbsRepRegDate;
	}
	public void setVbbsRepRegDate(String vbbsRepRegDate) {
		this.vbbsRepRegDate = vbbsRepRegDate;
	}
	public String getVbbsRepChecked() {
		return vbbsRepChecked;
	}
	public void setVbbsRepChecked(String vbbsRepChecked) {
		this.vbbsRepChecked = vbbsRepChecked;
	}
	
	
}
