package com.sp.mango.village.ad;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;
import com.sp.mango.village.BusinessAddr;
import com.sp.mango.village.MemberAddr;
import com.sp.mango.village.Reply;
import com.sp.mango.village.ReplyReport;
import com.sp.mango.village.VillageReport;

@Service("village.ad.villageAdService")
public class VillageAdServiceImpl implements VillageAdService {
	
	@Autowired
	private CommonDAO dao;
	
	@Override
	public void insertBoard(VillageAd dto) throws Exception {
		try {
			dao.insertData("ad.insertBoard", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public List<VillageAd> memberListBoard(Map<String, Object> map) {
		List<VillageAd> memberList = null;
		
		try {
			memberList = dao.selectList("ad.memberListBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberList;
	}
	
	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("ad.dataCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public VillageAd readBoard(int vNum) {
		VillageAd dto = null;
		
		try {
			dto = dao.selectOne("ad.readBoard", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	@Override
	public void updateHitCount(int vNum) throws Exception {
		try {
			dao.updateData("ad.updateHitCount", vNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public void updateBoard(VillageAd dto) throws Exception {
		try {
			dao.updateData("ad.updateBoard", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public void deleteBoard(int vNum, String userId, int membership) throws Exception {
		try {
			VillageAd dto = readBoard(vNum);
			if(dto == null || (membership < 51 && ! dto.getUserId().equals(userId))) {
				return;
			}
			dao.deleteData("ad.deleteBoard", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public List<MemberAddr> listMemberAddr(String userId) {
		List<MemberAddr> listMemberAddr = null;
		
		try {
			listMemberAddr = dao.selectList("ad.listMemberAddr", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMemberAddr;
	}
	@Override
	public int memAddrCount(String userId) {
		int result = 0;
		
		try {
			result = dao.selectOne("ad.memAddrCount", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public void insertBoardLike(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("ad.insertBoardLike", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public void deleteBoardLike(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("ad.deleteBoardLike", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public int boardLikeCount(int vNum) {
		int result = 0;
		
		try {
			result = dao.selectOne("ad.boardLikeCount", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public boolean userBoardLiked(Map<String, Object> map) {
		boolean result = false;
		
		try {
			VillageAd dto = dao.selectOne("ad.userBoardLiked", map);
			if(dto != null) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public void insertReply(Reply dto) throws Exception {
		try {
			dao.insertData("ad.insertReply", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public List<Reply> listReply(Map<String, Object> map) {
		List<Reply> list = null;
		
		try {
			list = dao.selectList("ad.listReply", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public int replyCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("ad.replyCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public void deleteReply(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("ad.deleteReply", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public List<Reply> listReplyAnswer(int vrAnswer) {
		List<Reply> list = null;
		
		try {
			list = dao.selectList("ad.listReplyAnswer", vrAnswer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public int replyAnswerCount(int vrAnswer) {
		int result = 0;
		
		try {
			result = dao.selectOne("ad.replyAnswerCount", vrAnswer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public void insertReplyLike(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("ad.insertReplyLike", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public Map<String, Object> replyLikeCount(Map<String, Object> map) {
		Map<String, Object> countMap = null;
		
		try {
			countMap = dao.selectOne("ad.replyLikeCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countMap;
	}
	@Override
	public List<VillageReport> listVreport() {
		List<VillageReport> listVreport = null;
		
		try {
			listVreport = dao.selectList("ad.listVreport");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listVreport;
	}
	@Override
	public void insertVreport(VillageReport dto) throws Exception {
		try {
			dao.insertData("ad.insertVreport", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public List<ReplyReport> listVRreport() {
		List<ReplyReport> listVRreport = null;
		
		try {
			listVRreport = dao.selectList("ad.listVRreport");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listVRreport;
	}
	@Override
	public void insertVRreport(ReplyReport dto) throws Exception {
		try {
			dao.insertData("ad.insertVRreport", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public void insertThumbnail(VillageAd dto) throws Exception {
		try {
			dao.insertData("ad.insertThumbnail", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<VillageAd> listThumbnail(int vNum) {
		List<VillageAd> listThumbnail = null;
		
		try {
			listThumbnail = dao.selectList("ad.listThumbnail", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listThumbnail;
	}
	@Override
	public BusinessAddr businessAddr(String userId) {
		BusinessAddr dto = null;
		
		try {
			dto = dao.selectOne("ad.businessAddr", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	

}
