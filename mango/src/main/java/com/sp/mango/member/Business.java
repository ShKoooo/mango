package com.sp.mango.member;

import org.springframework.web.multipart.MultipartFile;

public class Business {
	// businessProfile 테이블용
	private String userId;
	private String busNickName;
	private String busTel;
	private String busEmail;
	private Integer membershipNum;
	private String busImgSaveFileName;
	private String busImgOrigFileName;
	private MultipartFile profileImg;
	private Integer areaNum;
	private String busZip;
	private String busAddr1;
	private String busAddr2;
	private Double bpLat;
	private Double bpLon;
	
	private String email1;
	private String email2;
	private String tel1;
	private String tel2;
	private String tel3;
	// area 테이블용
	private String area1;
	private String area2;
	private String area3;
	private String bcodeCut;
	private String bcodeSigungu;
	private Double aLat;
	private Double aLon;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBusNickName() {
		return busNickName;
	}
	public void setBusNickName(String busNickName) {
		this.busNickName = busNickName;
	}
	public String getBusTel() {
		return busTel;
	}
	public void setBusTel(String busTel) {
		this.busTel = busTel;
	}
	public String getBusEmail() {
		return busEmail;
	}
	public void setBusEmail(String busEmail) {
		this.busEmail = busEmail;
	}
	public Integer getMembershipNum() {
		return membershipNum;
	}
	public void setMembershipNum(Integer membershipNum) {
		this.membershipNum = membershipNum;
	}
	public String getBusImgSaveFileName() {
		return busImgSaveFileName;
	}
	public void setBusImgSaveFileName(String busImgSaveFileName) {
		this.busImgSaveFileName = busImgSaveFileName;
	}
	public String getBusImgOrigFileName() {
		return busImgOrigFileName;
	}
	public void setBusImgOrigFileName(String busImgOrigFileName) {
		this.busImgOrigFileName = busImgOrigFileName;
	}
	public MultipartFile getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(MultipartFile profileImg) {
		this.profileImg = profileImg;
	}
	public Integer getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(Integer areaNum) {
		this.areaNum = areaNum;
	}
	public String getBusZip() {
		return busZip;
	}
	public void setBusZip(String busZip) {
		this.busZip = busZip;
	}
	public String getBusAddr1() {
		return busAddr1;
	}
	public void setBusAddr1(String busAddr1) {
		this.busAddr1 = busAddr1;
	}
	public String getBusAddr2() {
		return busAddr2;
	}
	public void setBusAddr2(String busAddr2) {
		this.busAddr2 = busAddr2;
	}
	public Double getBpLat() {
		return bpLat;
	}
	public void setBpLat(Double bpLat) {
		this.bpLat = bpLat;
	}
	public Double getBpLon() {
		return bpLon;
	}
	public void setBpLon(Double bpLon) {
		this.bpLon = bpLon;
	}
	
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getTel3() {
		return tel3;
	}
	public void setTel3(String tel3) {
		this.tel3 = tel3;
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
	public String getBcodeCut() {
		return bcodeCut;
	}
	public void setBcodeCut(String bcodeCut) {
		this.bcodeCut = bcodeCut;
	}
	public String getBcodeSigungu() {
		return bcodeSigungu;
	}
	public void setBcodeSigungu(String bcodeSigungu) {
		this.bcodeSigungu = bcodeSigungu;
	}
	public Double getaLat() {
		return aLat;
	}
	public void setaLat(Double aLat) {
		this.aLat = aLat;
	}
	public Double getaLon() {
		return aLon;
	}
	public void setaLon(Double aLon) {
		this.aLon = aLon;
	}
}
