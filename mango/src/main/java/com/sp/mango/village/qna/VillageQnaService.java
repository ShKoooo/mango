package com.sp.mango.village.qna;

import java.util.List;
import java.util.Map;

import com.sp.mango.village.MemberAddr;
import com.sp.mango.village.ReplyReport;
import com.sp.mango.village.VillageReport;

public interface VillageQnaService {
	public void insertBoard(VillageQna dto, String pathname) throws Exception;
	public List<VillageQna> listBoard(Map<String, Object> map); // 비회원, 주소 1개등록 회원 게시판 전체 리스트
	public List<VillageQna> memberListBoard(Map<String, Object> map); // 회원 지정 위치 5km 게시판 리스트
	public int dataCount(Map<String, Object> map);
	public int memberDataCount(Map<String, Object> map);
	public VillageQna readBoard(int vNum);
	public void updateHitCount(int vNum) throws Exception;
	public VillageQna preReadBoard(Map<String, Object> map);
	public VillageQna nextReadBoard(Map<String, Object> map);
	public void updateBoard(VillageQna dto) throws Exception;
	public void deleteBoard(int vNum, String userId, int membership) throws Exception;
	
	public List<MemberAddr> listMemberAddr(String userId); // 회원 주소정보
	public int memAddrCount(String userId); // 회원이 등록한 주소 개수
	
	public void insertBoardLike(Map<String, Object> map) throws Exception;
	public void deleteBoardLike(Map<String, Object> map) throws Exception;
	public int boardLikeCount(int vNum);
	public boolean userBoardLiked(Map<String, Object> map);
	
	public void insertReply(Reply dto) throws Exception;
	public List<Reply> listReply(Map<String, Object> map);
	public int replyCount(Map<String, Object> map);
	public void deleteReply(Map<String, Object> map) throws Exception;
	
	public List<Reply> listReplyAnswer(int vrAnswer);
	public int replyAnswerCount(int vrAnswer);
	
	public void insertReplyLike(Map<String, Object> map) throws Exception;
	public Map<String, Object> replyLikeCount(Map<String, Object> map);

	public List<VillageReport> listVreport();
	public void insertVreport(VillageReport dto) throws Exception;
	public List<ReplyReport> listVRreport();
	public void insertVRreport(ReplyReport dto) throws Exception;
}
