package com.sp.mango.product;

import java.util.List;
import java.util.Map;

public interface ProductService {
	public void insertProduct(Product dto, String pathname) throws Exception;
	public void updateProduct(Product dto, String pathname) throws Exception;
	public void deleteProduct(Product dto, String pathname) throws Exception;
	public List<Product> listProduct(Map<String, Object> map); // 전체 리스트 (비회원 및 주소 미등록 회원)
	public List<Product> memberListProduct(Map<String, Object> map); // 회원 지정 위치 기준 반경 5km내의 판매글
	public void updateHitCount(int pNum) throws Exception;
	
	public boolean userProductLiked(Map<String, Object> map); // 관심 여부
	
	
	public Product readProduct(int pNum);
	public List<Product> listCategory(); // 카테고리 리스트
	public List<MemberAddr> listMemberAddr(String userId); // 회원의 주소정보
	public int dataCount();
	public int memAddrCount(String userId); // 회원이 등록한 주소 갯수
	
	public void insertImgFile(Product dto) throws Exception;
	public Product readImgFile(int pImgNum);
	public void deleteImgFile(Map<String, Object> map) throws Exception;
	public List<Product> listImgFile(int pNum);
}
