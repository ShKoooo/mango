package com.sp.mango.mypage;

import java.util.List;
import java.util.Map;

import com.sp.mango.member.Business;
import com.sp.mango.member.MemberAddr;

public interface MypageService {
	public MannerProfile readMannerProfile(String userId) throws Exception;
	public void insertMannerProfile(String userId) throws Exception;
	public List<MemberAddr> listMemberAddr(String userId) throws Exception;
	public Integer businessDuplCheck(String userId) throws Exception;
	public Business readBusiness(String userId) throws Exception;
	
	public List<PickedUser> listPickedUser(String userId) throws Exception;
	public List<BlockedUser> listBlockedUser(String userId) throws Exception;
	public List<Keyword> listKeyword(String userId) throws Exception;
	
	public int myPickCount(Map<String, Object> map) throws Exception;
	public List<PickedUser> listMyPickedUser(Map<String, Object> map) throws Exception;
	public String readUserIdByNickName(String userNickName) throws Exception;
	public void insertPickedUser(Map<String, Object> map) throws Exception;
	
	public void deleteMySelection(Map<String, Object> map) throws Exception;
	
	public int myBlockCount(Map<String, Object> map) throws Exception;
	public List<BlockedUser> listMyBlockedUser(Map<String, Object> map) throws Exception;
	public void insertBlockedUser(Map<String, Object> map) throws Exception;
	
	public int myKeywordCount(Map<String, Object> map) throws Exception;
	public List<Keyword> listMyKeyword(Map<String,Object> map) throws Exception;
	public void insertKeyword(Map<String,Object> map) throws Exception;
	
	public List<Note> listNoteSender(Map<String,Object> map) throws Exception;
	public List<Note> listNoteReceiver(Map<String,Object> map) throws Exception;
	public Note readNoteLastTime(Map<String,Object> map) throws Exception;
	public List<Note> listNoteNote(Map<String, Object> map) throws Exception;
	public void insertNote(Map<String, Object> map) throws Exception;
	public int getBlockCount(String userId) throws Exception;
	public void updateNoteReadDate(Map<String, Object> map) throws Exception;
	public void updateSendDelete(Map<String,Object> map) throws Exception;
	public void updateReceiveDelete(Map<String,Object> map) throws Exception;
	public void updateUserAllDelete(Map<String,Object> map) throws Exception;
	public void updateEntireMyNoteDelete(Map<String,Object> map) throws Exception;
	
	public List<Rating> listRating(Map<String,Object> map) throws Exception;
	public List<Rating> listProductRating(Map<String,Object> map) throws Exception;
	public List<Rating> listGiftyRating(Map<String,Object> map) throws Exception;
	public int countRating(String userId) throws Exception;
	public int countProductRating(String userId) throws Exception;
	public int countGiftyRating(String userId) throws Exception;
	
	public int countActivity(Map<String,Object> map) throws Exception;
	public List<Activity> listActivity(Map<String,Object> map) throws Exception;
	
	public int countAccount(Map<String,Object> map) throws Exception;
	public List<Account> listAccount(Map<String,Object> map) throws Exception;
	
	public List<RepMemberReason> listRepMemReason() throws Exception;
	public void insertReportMember(Map<String,Object> map) throws Exception;
	
	public int countNotReadNote (Map<String, Object> countNoteMap);
	public int countNotReadEachNote (Map<String,Object> map);
}
