package com.sp.mango.village;

public class MemberAddr {
	private String userId;
	
	private int maNum; // 주소 번호
	private String maAddr1;
	private String maAddr2;
	private String maZip; // 우편 번호
	
	private double maLat; // 위도
	private double maLon; // 경도
	private int areaNum; // 지역번호
	
	private String area1; // 광역시도
	private String area2; // 시군구
	private String area3; // 하위 행정구역
	private double aLat; // 지역 위도
	private double aLon; // 지역 경도
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getMaNum() {
		return maNum;
	}
	public void setMaNum(int maNum) {
		this.maNum = maNum;
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
	public String getMaZip() {
		return maZip;
	}
	public void setMaZip(String maZip) {
		this.maZip = maZip;
	}
	public Double getMaLat() {
		return maLat;
	}
	public double getMaLon() {
		return maLon;
	}
	public void setMaLon(double maLon) {
		this.maLon = maLon;
	}
	public void setMaLat(double maLat) {
		this.maLat = maLat;
	}
	public int getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(int areaNum) {
		this.areaNum = areaNum;
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
	
	
}
