package com.sp.mango.product;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Product {
	private int pNum; // 상품번호
	private String pSubject; // 제목
	private String pStatus; // 상품 현재 상태 (판매중, 예약중, 판매완료)
	private String pContent; // 설명
	private int pPrice; // 가격
	private int pHitCount; // 조회수
	private String pRegDate; // 상품 등록일
	private String pUpdate_date; // 끌어올린 날짜
	private String pSoldDate; // 판매 날짜

	private String pIsProposable; // 가격제안 허용여부(T/F)
	private int pPropPrice; // 제안 가격

	private String userId; // 회원 아이디
	private String userNickName; // 회원 닉네임
	
	private int areaNum; // 판매지역번호
	private double pLat; // 위도
	private double pLon; // 경도
	
	private int pcNum; // 카테고리 번호
	private String pcName; // 카테고리 이름
	
	private int pImgNum; // 상품 이미지번호
	private String pImgSaveFileName; // 상품 이미지 저장명
	private List<MultipartFile> selectFile;
	
	public int getpNum() {
		return pNum;
	}
	public void setpNum(int pNum) {
		this.pNum = pNum;
	}
	public String getpSubject() {
		return pSubject;
	}
	public void setpSubject(String pSubject) {
		this.pSubject = pSubject;
	}
	public String getpStatus() {
		return pStatus;
	}
	public void setpStatus(String pStatus) {
		this.pStatus = pStatus;
	}
	public String getpContent() {
		return pContent;
	}
	public void setpContent(String pContent) {
		this.pContent = pContent;
	}
	public int getpPrice() {
		return pPrice;
	}
	public void setpPrice(int pPrice) {
		this.pPrice = pPrice;
	}
	public int getpHitCount() {
		return pHitCount;
	}
	public void setpHitCount(int pHitCount) {
		this.pHitCount = pHitCount;
	}
	public String getpRegDate() {
		return pRegDate;
	}
	public void setpRegDate(String pRegDate) {
		this.pRegDate = pRegDate;
	}
	public String getpUpdate_date() {
		return pUpdate_date;
	}
	public void setpUpdate_date(String pUpdate_date) {
		this.pUpdate_date = pUpdate_date;
	}
	public String getpSoldDate() {
		return pSoldDate;
	}
	public void setpSoldDate(String pSoldDate) {
		this.pSoldDate = pSoldDate;
	}
	public String getpIsProposable() {
		return pIsProposable;
	}
	public void setpIsProposable(String pIsProposable) {
		this.pIsProposable = pIsProposable;
	}
	public int getpPropPrice() {
		return pPropPrice;
	}
	public void setpPropPrice(int pPropPrice) {
		this.pPropPrice = pPropPrice;
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
	public int getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(int areaNum) {
		this.areaNum = areaNum;
	}
	public double getpLat() {
		return pLat;
	}
	public void setpLat(double pLat) {
		this.pLat = pLat;
	}
	public double getpLon() {
		return pLon;
	}
	public void setpLon(double pLon) {
		this.pLon = pLon;
	}
	public int getPcNum() {
		return pcNum;
	}
	public void setPcNum(int pcNum) {
		this.pcNum = pcNum;
	}
	public String getPcName() {
		return pcName;
	}
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	public int getpImgNum() {
		return pImgNum;
	}
	public void setpImgNum(int pImgNum) {
		this.pImgNum = pImgNum;
	}
	public String getpImgSaveFileName() {
		return pImgSaveFileName;
	}
	public void setpImgSaveFileName(String pImgSaveFileName) {
		this.pImgSaveFileName = pImgSaveFileName;
	}
	public List<MultipartFile> getSelectFile() {
		return selectFile;
	}
	public void setSelectFile(List<MultipartFile> selectFile) {
		this.selectFile = selectFile;
	}
}
