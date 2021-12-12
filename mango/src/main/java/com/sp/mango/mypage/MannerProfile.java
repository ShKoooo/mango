package com.sp.mango.mypage;

public class MannerProfile {
	private String userId;
	private int mannerStar;
	private int productStar;
	private double minusDeg;
	private double mannerDeg;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getMannerStar() {
		return mannerStar;
	}
	public void setMannerStar(int mannerStar) {
		this.mannerStar = mannerStar;
	}
	public int getProductStar() {
		return productStar;
	}
	public void setProductStar(int productStar) {
		this.productStar = productStar;
	}
	public double getMinusDeg() {
		return minusDeg;
	}
	public void setMinusDeg(double minusDeg) {
		this.minusDeg = minusDeg;
	}
	public double getMannerDeg() {
		return mannerDeg;
	}
	public void setMannerDeg(int mannerStar, int productStar, double minusDeg) {
		this.mannerDeg = 36.5 + 0.1*mannerStar + 0.1*productStar - minusDeg;
	}
}
