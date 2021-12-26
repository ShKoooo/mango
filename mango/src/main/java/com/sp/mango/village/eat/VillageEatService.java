package com.sp.mango.village.eat;

import java.util.List;
import java.util.Map;

import com.sp.mango.village.MemberAddr;
import com.sp.mango.village.ReplyReport;
import com.sp.mango.village.VillageReport;
import com.sp.mango.village.Reply;

public interface VillageEatService {
	public void insertBoard(VillageEat dto, String pathname) throws Exception;
	public List<VillageEat> listBoard(Map<String, Object> map);
	public List<VillageEat> memberListBoard(Map<String, Object> map);
	public int dataCount(Map<String, Object> map);
	public int memberDataCount(Map<String, Object> map);
	public VillageEat readBoard(int vNum);
	public void updateHitCount(int vNum) throws Exception;
	public void updateBoard(VillageEat dto) throws Exception;
	public void deleteBoard(int vNum, String userId, int membership) throws Exception;
	
	public List<MemberAddr> listMemberAddr(String userId);
	public int memAddrCount(String userId);
	
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
