package com.sp.mango.product;

import java.util.List;
import java.util.Map;

public interface ProductService {
	public void insertProduct(Product dto, String pathname) throws Exception;
	public void updateProduct(Product dto, String pathname) throws Exception;
	public void deleteProduct(Product dto, String pathname) throws Exception;
	public List<Product> listProduct(Map<String, Object> map);
	public Product readProduct(int pNum);
	public List<Product> listCategory(); // 카테고리 리스트
	public List<MemberAddr> listMemberAddr(String userId); // 회원의 주소정보
	
	
	public void insertImgFile(Product dto) throws Exception;
	public Product readImgFile(int pImgNum);
	public void deleteImgFile(Map<String, Object> map) throws Exception;
	public List<Product> listImgFile(int pNum);
}
