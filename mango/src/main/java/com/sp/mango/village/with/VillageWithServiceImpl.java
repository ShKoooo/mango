package com.sp.mango.village.with;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;
import com.sp.mango.village.MemberAddr;
import com.sp.mango.village.Reply;
import com.sp.mango.village.ReplyReport;
import com.sp.mango.village.VillageReport;

@Service("village.with.villageWithService")
public class VillageWithServiceImpl implements VillageWithService{

	@Autowired
	private CommonDAO dao;
	
	@Override
	public void insertBoard(VillageWith dto) throws Exception {
		try {
			dao.insertData("with.insertBoard", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<VillageWith> memberListBoard(Map<String, Object> map) {
		List<VillageWith> memberList = null;
		
		try {
			memberList = dao.selectList("with.memberListBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberList;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("with.dataCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public VillageWith readBoard(int vNum) {
		VillageWith dto = null;
		
		try {
			dto = dao.selectOne("with.readBoard", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public void updateHitCount(int vNum) throws Exception {
		try {
			dao.updateData("with.updateHitCount", vNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void updateBoard(VillageWith dto) throws Exception {
		try {
			dao.updateData("with.updateBoard", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteBoard(int vNum, String userId, int membership) throws Exception {
		try {
			VillageWith dto = readBoard(vNum);
			if(dto == null || (membership < 51 && ! dto.getUserId().equals(userId))) {
				return;
			}
			
			dao.deleteData("with.deleteBoard", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}	}

	@Override
	public List<MemberAddr> listMemberAddr(String userId) {
		List<MemberAddr> listMemberAddr = null;
		
		try {
			listMemberAddr = dao.selectList("with.listMemberAddr", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMemberAddr;
	}

	@Override
	public int memAddrCount(String userId) {
		int result = 0;
		
		try {
			result = dao.selectOne("with.memAddrCount", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void insertBoardLike(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("with.insertBoardLike", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteBoardLike(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("with.deleteBoardLike", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int boardLikeCount(int vNum) {
		int result = 0;
		
		try {
			result = dao.selectOne("with.boardLikeCount", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean userBoardLiked(Map<String, Object> map) {
		boolean result = false;
		
		try {
			VillageWith dto = dao.selectOne("with.userBoardLiked", map);
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
			dao.insertData("with.insertReply", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Reply> listReply(Map<String, Object> map) {
		List<Reply> list = null;
		
		try {
			list = dao.selectList("with.listReply", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int replyCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("with.replyCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void deleteReply(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("with.deleteReply", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Reply> listReplyAnswer(int vrAnswer) {
		List<Reply> list = null;
		
		try {
			list = dao.selectList("with.listReplyAnswer", vrAnswer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int replyAnswerCount(int vrAnswer) {
		int result = 0;
		
		try {
			result = dao.selectOne("with.replyAnswerCount", vrAnswer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void insertReplyLike(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("with.insertReplyLike", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Map<String, Object> replyLikeCount(Map<String, Object> map) {
		Map<String, Object> countMap = null;
		
		try {
			countMap = dao.selectOne("with.replyLikeCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countMap;
	}

	@Override
	public List<VillageReport> listVreport() {
		List<VillageReport> listVreport = null;
		
		try {
			listVreport = dao.selectList("with.listVreport");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listVreport;
	}

	@Override
	public void insertVreport(VillageReport dto) throws Exception {
		try {
			dao.insertData("with.insertVreport", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<ReplyReport> listVRreport() {
		List<ReplyReport> listVRreport = null;
		
		try {
			listVRreport = dao.selectList("with.listVRreport");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listVRreport;
	}

	@Override
	public void insertVRreport(ReplyReport dto) throws Exception {
		try {
			dao.insertData("with.insertVRreport", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
