package com.sp.mango.product;

public class MemberAddr {
	private int maNum;
	private String maZip;
	private String maAddr1;
	private String maAddr2;
	private String userId;
	
	private double maLat; // 위도
	private double maLon; // 경도
	private int areaNum; // 지역번호
	
	
	public int getMaNum() {
		return maNum;
	}
	public void setMaNum(int maNum) {
		this.maNum = maNum;
	}
	public String getMaZip() {
		return maZip;
	}
	public void setMaZip(String maZip) {
		this.maZip = maZip;
	}
	public String getMaAddr1() {
		return maAddr1;
	}
	public void setMaAddr1(String maAddr1) {
		this.maAddr1 = maAddr1;
	}
	public String getMaAddr2() {
		return maAddr2;
	}
	public void setMaAddr2(String maAddr2) {
		this.maAddr2 = maAddr2;
	}
	public double getMaLat() {
		return maLat;
	}
	public void setMaLat(double maLat) {
		this.maLat = maLat;
	}
	public double getMaLon() {
		return maLon;
	}
	public void setMaLon(double maLon) {
		this.maLon = maLon;
	}
	public int getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(int areaNum) {
		this.areaNum = areaNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
