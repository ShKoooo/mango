package com.sp.mango.village.lost;

import org.springframework.web.multipart.MultipartFile;

public class VillageLost {
	private int listNum;
	
	private int vNum;
	private String userId;
	private String userNickName;
	private String subject;
	private String content;
	private String reg_date;
	private int hitCount;
	
	private int replyCount;
	private int boardLikeCount;
	
	private int areaNum;
	private String addr2;
	private double vBlat;
	private double vBlon;
	
	private String maAddr1;
	private String area1;
	private String area3;
	
	// 썸네일용 이미지 업로드
	private int vimgNum;
	private String vimgFilename;
	private MultipartFile selectFile;
	
	private String thumbnail;
	
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public int getvNum() {
		return vNum;
	}
	public void setvNum(int vNum) {
		this.vNum = vNum;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getHitCount() {
		return hitCount;
	}
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public int getBoardLikeCount() {
		return boardLikeCount;
	}
	public void setBoardLikeCount(int boardLikeCount) {
		this.boardLikeCount = boardLikeCount;
	}
	public int getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(int areaNum) {
		this.areaNum = areaNum;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public double getvBlat() {
		return vBlat;
	}
	public void setvBlat(double vBlat) {
		this.vBlat = vBlat;
	}
	public double getvBlon() {
		return vBlon;
	}
	public void setvBlon(double vBlon) {
		this.vBlon = vBlon;
	}
	public String getMaAddr1() {
		return maAddr1;
	}
	public void setMaAddr1(String maAddr1) {
		this.maAddr1 = maAddr1;
	}
	public String getArea1() {
		return area1;
	}
	public void setArea1(String area1) {
		this.area1 = area1;
	}
	public String getArea3() {
		return area3;
	}
	public void setArea3(String area3) {
		this.area3 = area3;
	}
	public int getVimgNum() {
		return vimgNum;
	}
	public void setVimgNum(int vimgNum) {
		this.vimgNum = vimgNum;
	}
	public String getVimgFilename() {
		return vimgFilename;
	}
	public void setVimgFilename(String vimgFilename) {
		this.vimgFilename = vimgFilename;
	}
	public MultipartFile getSelectFile() {
		return selectFile;
	}
	public void setSelectFile(MultipartFile selectFile) {
		this.selectFile = selectFile;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	
}
