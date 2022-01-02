package com.sp.mango.product;

import java.util.List;
import java.util.Map;

import com.sp.mango.mypage.Note;

public interface ProductService {
	public void insertProduct(Product dto) throws Exception;
	public void updateProduct(Product dto) throws Exception;
	public void deleteProduct(int pNum) throws Exception;
	public List<Product> listProduct(Map<String, Object> map); // 전체 리스트 (비회원 및 주소 미등록 회원)
	public List<Product> memberListProduct(Map<String, Object> map); // 회원 지정 위치 기준 반경 5km내의 판매글
	public List<Product> popularList(Map<String, Object> map); // 인기글 리스트
	public void updateHitCount(int pNum) throws Exception;
	
	public int pWishCount(int pNum); // 특정 게시글이 받은 관심 개수
	
	public void insertProductWish(Map<String, Object> map) throws Exception; // 관심 등록
	public void deleteProductWish(Map<String, Object> map) throws Exception; // 관심 취소
	public int productWishCount(int pNum); // 관심 개수
	public boolean userProductWished(Map<String, Object> map) throws Exception; // 관심 여부
	
	public Product readProduct(int pNum);
	public List<Product> listCategory(); // 카테고리 리스트
	public List<MemberAddr> listMemberAddr(String userId); // 회원의 주소정보
	public int dataCount(Map<String, Object> map);
	public int memAddrCount(String userId); // 회원이 등록한 주소 갯수
	
	public void insertImgFile(Product dto) throws Exception;
	public Product readImgFile(int pImgNum);
	public void deleteImgFile(Map<String, Object> map) throws Exception;
	public List<Product> listImgFile(int pNum);

	public void updateSoldDate(Product dto) throws Exception; // 판매일자 업데이트
	public void updateDate(int pNum, String userId) throws Exception; // 게시글 끌어올리기
	
	public List<ProductReport> listPreport(); // 신고항목 리스트
	public void insertPreport(ProductReport dto) throws Exception; // 신고 등록
	
	public String pUpOkDate(int pNum); // 끌올 가능한 날짜
	
	// 쪽지 보내기
	public void sendMsg(Note notedto) throws Exception;
	
	// 리뷰 등록
	public void insertPreview(Preview dto) throws Exception;
	
	// 프로필 사진
	public String userImgSaveFileName(int pNum);
	
	// 리뷰 요청을 위한 받은 쪽지 리스트
	public List<Product> receiveNoteList(String userId);
	
	// 리뷰요청시 가계부 등록
	public void insertMyAccount(Product dto) throws Exception;
	
	// 거래완료로 변경
	public void updatePstatus(int pNum) throws Exception;
	
	// 예약중인 상품, 유저아이디 등록
	public void insertBook(Product dto) throws Exception;
	
	// 예약내역 검색
	public List<Product> selectBook(int pNum);
	
}
