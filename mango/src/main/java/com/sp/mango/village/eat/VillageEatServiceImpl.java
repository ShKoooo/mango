package com.sp.mango.village.eat;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;
import com.sp.mango.village.MemberAddr;
import com.sp.mango.village.ReplyReport;
import com.sp.mango.village.VillageReport;

@Service("village.eat.villageEatService")
public class VillageEatServiceImpl implements VillageEatService {
	@Autowired
	private CommonDAO dao;
	
	@Override
	public void insertBoard(VillageEat dto, String pathname) throws Exception {
		try {
			dao.insertData("eat.insertBoard", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<VillageEat> listBoard(Map<String, Object> map) {
		List<VillageEat> list = null;
		
		try {
			list = dao.selectList("eat.listBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<VillageEat> memberListBoard(Map<String, Object> map) {
		List<VillageEat> memberList = null;
		
		try {
			memberList = dao.selectList("eat.memberListBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return memberList;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("eat.dataCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int memberDataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("eat.memberDataCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public VillageEat readBoard(int vNum) {
		VillageEat dto = null;
		
		try {
			dto = dao.selectOne("eat.readBoard", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@Override
	public void updateHitCount(int vNum) throws Exception {
		try {
			dao.updateData("eat.updateHitCount", vNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void updateBoard(VillageEat dto) throws Exception {
		try {
			dao.updateData("eat.updateBoard", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteBoard(int vNum, String userId, int membership) throws Exception {
		try {
			VillageEat dto = readBoard(vNum);
			if(dto == null || (membership < 51 && ! dto.getUserId().equals(userId))) {
				return;
			}
			
			dao.deleteData("eat.deleteBoard", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MemberAddr> listMemberAddr(String userId) {
		List<MemberAddr> listMemberAddr = null;
		
		try {
			listMemberAddr = dao.selectList("eat.listMemberAddr", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMemberAddr;
	}

	@Override
	public int memAddrCount(String userId) {
		int result = 0;
		
		try {
			result = dao.selectOne("eat.memAddrCount", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void insertBoardLike(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("eat.insertBoardLike", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void deleteBoardLike(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("eat.deleteBoardLike", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int boardLikeCount(int vNum) {
		int result = 0;
		
		try {
			result = dao.selectOne("eat.boardLikeCount", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean userBoardLiked(Map<String, Object> map) {
		boolean result = false;
		
		try {
			VillageEat dto = dao.selectOne("eat.userBoardLiked", map);
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
			dao.insertData("eat.insertReply", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Reply> listReply(Map<String, Object> map) {
		List<Reply> list = null;
		
		try {
			list = dao.selectList("eat.listReply", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int replyCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteReply(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Reply> listReplyAnswer(int vrAnswer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int replyAnswerCount(int vrAnswer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertReplyLike(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> replyLikeCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VillageReport> listVreport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertVreport(VillageReport dto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ReplyReport> listVRreport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertVRreport(ReplyReport dto) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
