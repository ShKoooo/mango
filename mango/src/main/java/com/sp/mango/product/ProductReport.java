package com.sp.mango.product;

public class ProductReport {
	private int repPrdNum; // 상품 신고번호
	private int rpReasonNum; // 상품신고사유 항목번호
	private String rpReasonName; // 상품신고사유 항목이름
	private String repPrdContent; // 상품 신고 상세
	private String repPrdRegDate; // 상품 신고 날짜
	private String repPrdChecked; // 상품신고 해결여부(T/F)
	private String userId; // 회원 아이디
	private int pNum; // 상품 번호
	
	
	public int getRepPrdNum() {
		return repPrdNum;
	}
	public void setRepPrdNum(int repPrdNum) {
		this.repPrdNum = repPrdNum;
	}
	public int getRpReasonNum() {
		return rpReasonNum;
	}
	public void setRpReasonNum(int rpReasonNum) {
		this.rpReasonNum = rpReasonNum;
	}
	public String getRpReasonName() {
		return rpReasonName;
	}
	public void setRpReasonName(String rpReasonName) {
		this.rpReasonName = rpReasonName;
	}
	public String getRepPrdContent() {
		return repPrdContent;
	}
	public void setRepPrdContent(String repPrdContent) {
		this.repPrdContent = repPrdContent;
	}
	public String getRepPrdRegDate() {
		return repPrdRegDate;
	}
	public void setRepPrdRegDate(String repPrdRegDate) {
		this.repPrdRegDate = repPrdRegDate;
	}
	public String getRepPrdChecked() {
		return repPrdChecked;
	}
	public void setRepPrdChecked(String repPrdChecked) {
		this.repPrdChecked = repPrdChecked;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getpNum() {
		return pNum;
	}
	public void setpNum(int pNum) {
		this.pNum = pNum;
	}
}
