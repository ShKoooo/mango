package com.sp.mango.village.qna;

import java.util.List;
import java.util.Map;

public interface VillageQnaService {
	public void insertBoard(VillageQna dto, String pathname) throws Exception;
	public List<VillageQna> listBoard(Map<String, Object> map);
	public int dataCount(Map<String, Object> map);
	public VillageQna readBoard(int vNum);
	public void updateHitCount(int vNum) throws Exception;
	public VillageQna preReadBoard(Map<String, Object> map);
	public VillageQna nextReadBoard(Map<String, Object> map);
	public void updateBoard(VillageQna dto) throws Exception;
	public void deleteBoard(int vNum, String userId, int membership) throws Exception;
	
	public void insertBoardLike(Map<String, Object> map) throws Exception;
	public void deleteBoardLike(Map<String, Object> map) throws Exception;
	public int boardLikeCount(int vNum);
	public boolean userBoardLiked(Map<String, Object> map);
	
	public void insertReply(Reply dto) throws Exception;
	public List<Reply> listReply(Map<String, Object> map);
	public int replyCount(Map<String, Object> map);
	public void deleteReply(Map<String, Object> map) throws Exception;
	
	public List<Reply> listReplyAnswer(int answer);
	public int replyAnswerCount(int answer);
	
	public void insertReplyLike(Map<String, Object> map) throws Exception;
	public Map<String, Object> replyLikeCount(Map<String, Object> map);
	
}
