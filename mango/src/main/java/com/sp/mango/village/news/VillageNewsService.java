package com.sp.mango.village.news;

import java.util.List;
import java.util.Map;

import com.sp.mango.village.MemberAddr;
import com.sp.mango.village.Reply;
import com.sp.mango.village.ReplyReport;
import com.sp.mango.village.VillageReport;

public interface VillageNewsService {
	public void insertBoard(VillageNews dto) throws Exception;
	public List<VillageNews> memberListBoard(Map<String, Object> map);
	public int dataCount(Map<String, Object> map);
	public VillageNews readBoard(int vNum);
	public void updateHitCount(int vNum) throws Exception;
	public void updateBoard(VillageNews dto) throws Exception;
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
