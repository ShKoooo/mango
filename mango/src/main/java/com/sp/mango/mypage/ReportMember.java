package com.sp.mango.mypage;

public class ReportMember {
	private int repMemNum;
	private String repMemContent;
	private int repReasonNum;
	private String userId;
	private String target_id;
	
	public int getRepMemNum() {
		return repMemNum;
	}
	public void setRepMemNum(int repMemNum) {
		this.repMemNum = repMemNum;
	}
	public String getRepMemContent() {
		return repMemContent;
	}
	public void setRepMemContent(String repMemContent) {
		this.repMemContent = repMemContent;
	}
	public int getRepReasonNum() {
		return repReasonNum;
	}
	public void setRepReasonNum(int repReasonNum) {
		this.repReasonNum = repReasonNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTarget_id() {
		return target_id;
	}
	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}
}
