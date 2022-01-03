package com.sp.mango.gifty;

import java.util.List;
import java.util.Map;

import com.sp.mango.mypage.Note;

public interface GiftyService {
	public void insertGifty(Gifty dto) throws Exception;
	public List<Gifty> listGifty(Map<String, Object> map);
	public int dataCount(Map<String, Object> map);
	public Gifty readGifty(int gnum);
	public void updateHitCount(int gnum) throws Exception;
	public void updateGifty(Gifty dto) throws Exception;
	public void deleteGifty(int gnum, String userId) throws Exception;

	public List<Gifty> listGcategory();
	
	public void insertGwish(Map<String, Object> map) throws Exception;
	public Map<String, Object> listGwish(Map<String, Object> map);
	public int gwishCount(int gNum);
	public int giftyWishCount(int gNum);
	public void deleteGwish(Map<String, Object> map) throws Exception;
	public boolean userGwished(Map<String, Object> map);
	
	public void updateSdate(int gnum, String userId) throws Exception;
	public void updateGstatus(int gNum) throws Exception;
	
	// 기프티콘 신고
	public List<GiftyReport> listGreport();
	public void insertGreport(GiftyReport dto) throws Exception;
	
	// 끌어올리기
	public void updateDate(int gnum, String userId) throws Exception;
	public String gUpOkDate(int gnum);
	
	// 쪽지 보내기
	public void sendMsg(Note dto) throws Exception;
	public void getUserId(int gnum);
	
	// 인기매물 
	public List<Gifty> listPop(Map<String, Object> map);

	public String userImgSaveFileName(int gNum) throws Exception;

	public List<Gifty> listTargetId(Map<String, Object> map);
	
	public void insertMyAccount(Gifty dto) throws Exception;

	public void insertGreview(Greview dto) throws Exception;
	
	public void insertBook(Gifty dto) throws Exception;
	
	// 예약 알림 받자
	public List<Gifty> selectBook(int gNum);
}
