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
	
	private String area1; // 광역시도
	private String area2; // 시군구
	private String area3; // 하위 행정구역
	private double aLat; // 지역 위도
	private double aLon; // 지역 경도
	
	
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
	public String getArea1() {
		return area1;
	}
	public void setArea1(String area1) {
		this.area1 = area1;
	}
	public String getArea2() {
		return area2;
	}
	public void setArea2(String area2) {
		this.area2 = area2;
	}
	public String getArea3() {
		return area3;
	}
	public void setArea3(String area3) {
		this.area3 = area3;
	}
	public double getaLat() {
		return aLat;
	}
	public void setaLat(double aLat) {
		this.aLat = aLat;
	}
	public double getaLon() {
		return aLon;
	}
	public void setaLon(double aLon) {
		this.aLon = aLon;
	}
	@Override
	public String toString() {
		return "MemberAddr [maNum=" + maNum + ", maZip=" + maZip + ", maAddr1=" + maAddr1 + ", maAddr2=" + maAddr2
				+ ", userId=" + userId + ", maLat=" + maLat + ", maLon=" + maLon + ", areaNum=" + areaNum + ", area1="
				+ area1 + ", area2=" + area2 + ", area3=" + area3 + ", aLat=" + aLat + ", aLon=" + aLon + "]";
	}
	
}
