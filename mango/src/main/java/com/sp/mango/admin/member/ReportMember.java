package com.sp.mango.admin.member;

public class ReportMember {
	private int repMemNum;
	private String repMemContent;
	private int rmReasonNum;
	private String rmReasonName;
	private String repMemRegDate;
	private String repMemChecked;
	private String userId;
	private String reporter;
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
	public int getRmReasonNum() {
		return rmReasonNum;
	}
	public void setRmReasonNum(int rmReasonNum) {
		this.rmReasonNum = rmReasonNum;
	}
	public String getRmReasonName() {
		return rmReasonName;
	}
	public void setRmReasonName(String rmReasonName) {
		this.rmReasonName = rmReasonName;
	}
	public String getRepMemRegDate() {
		return repMemRegDate;
	}
	public void setRepMemRegDate(String repMemRegDate) {
		this.repMemRegDate = repMemRegDate;
	}
	public String getRepMemChecked() {
		return repMemChecked;
	}
	public void setRepMemChecked(String repMemChecked) {
		this.repMemChecked = repMemChecked;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public String getTarget_id() {
		return target_id;
	}
	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}	
}
