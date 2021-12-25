package com.sp.mango.mypage;

public class Rating {
	private String pgType;
	private int pgNum;
	private String subject;
	private String buyerId;
	private String buyerNick;
	private Integer mannerStar;
	private Integer prdStar;
	private String sellerId;
	private String reviewContent;
	private String userImgSaveFileName;
	private String soldDate;
	
	public String getPgType() {
		return pgType;
	}
	public void setPgType(String pgType) {
		this.pgType = pgType;
	}
	public int getPgNum() {
		return pgNum;
	}
	public void setPgNum(int pgNum) {
		this.pgNum = pgNum;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerNick() {
		return buyerNick;
	}
	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
	public Integer getMannerStar() {
		return mannerStar;
	}
	public void setMannerStar(Integer mannerStar) {
		this.mannerStar = mannerStar;
	}
	public Integer getPrdStar() {
		return prdStar;
	}
	public void setPrdStar(Integer prdStar) {
		this.prdStar = prdStar;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getReviewContent() {
		return reviewContent;
	}
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
	public String getUserImgSaveFileName() {
		return userImgSaveFileName;
	}
	public void setUserImgSaveFileName(String userImgSaveFileName) {
		this.userImgSaveFileName = userImgSaveFileName;
	}
	public String getSoldDate() {
		return soldDate;
	}
	public void setSoldDate(String soldDate) {
		this.soldDate = soldDate;
	}
}
