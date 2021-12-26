package com.sp.mango.village.help;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;
import com.sp.mango.village.MemberAddr;
import com.sp.mango.village.Reply;
import com.sp.mango.village.ReplyReport;
import com.sp.mango.village.VillageReport;

@Service("village.help.villageHelpService")
public class VillageHelpServiceImpl implements VillageHelpService{

	@Autowired
	private CommonDAO dao;
	
	@Override
	public void insertBoard(VillageHelp dto) throws Exception {
		try {
			dao.insertData("help.insertBoard", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<VillageHelp> memberListBoard(Map<String, Object> map) {
		List<VillageHelp> memberList = null;
		
		try {
			memberList = dao.selectList("help.memberListBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberList;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("help.dataCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public VillageHelp readBoard(int vNum) {
		VillageHelp dto = null;
		
		try {
			dto = dao.selectOne("help.readBoard", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public void updateHitCount(int vNum) throws Exception {
		try {
			dao.updateData("help.updateHitCount", vNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void updateBoard(VillageHelp dto) throws Exception {
		try {
			dao.updateData("help.updateBoard", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteBoard(int vNum, String userId, int membership) throws Exception {
		try {
			VillageHelp dto = readBoard(vNum);
			if(dto == null || (membership < 51 && ! dto.getUserId().equals(userId))) {
				return;
			}
			
			dao.deleteData("help.deleteBoard", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}	}

	@Override
	public List<MemberAddr> listMemberAddr(String userId) {
		List<MemberAddr> listMemberAddr = null;
		
		try {
			listMemberAddr = dao.selectList("help.listMemberAddr", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMemberAddr;
	}

	@Override
	public int memAddrCount(String userId) {
		int result = 0;
		
		try {
			result = dao.selectOne("help.memAddrCount", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void insertBoardLike(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("help.insertBoardLike", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteBoardLike(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("help.deleteBoardLike", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int boardLikeCount(int vNum) {
		int result = 0;
		
		try {
			result = dao.selectOne("help.boardLikeCount", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean userBoardLiked(Map<String, Object> map) {
		boolean result = false;
		
		try {
			VillageHelp dto = dao.selectOne("help.userBoardLiked", map);
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
			dao.insertData("help.insertReply", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Reply> listReply(Map<String, Object> map) {
		List<Reply> list = null;
		
		try {
			list = dao.selectList("help.listReply", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int replyCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("help.replyCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void deleteReply(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("help.deleteReply", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Reply> listReplyAnswer(int vrAnswer) {
		List<Reply> list = null;
		
		try {
			list = dao.selectList("help.listReplyAnswer", vrAnswer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int replyAnswerCount(int vrAnswer) {
		int result = 0;
		
		try {
			result = dao.selectOne("help.replyAnswerCount", vrAnswer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void insertReplyLike(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("help.insertReplyLike", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Map<String, Object> replyLikeCount(Map<String, Object> map) {
		Map<String, Object> countMap = null;
		
		try {
			countMap = dao.selectOne("help.replyLikeCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countMap;
	}

	@Override
	public List<VillageReport> listVreport() {
		List<VillageReport> listVreport = null;
		
		try {
			listVreport = dao.selectList("help.listVreport");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listVreport;
	}

	@Override
	public void insertVreport(VillageReport dto) throws Exception {
		try {
			dao.insertData("help.insertVreport", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<ReplyReport> listVRreport() {
		List<ReplyReport> listVRreport = null;
		
		try {
			listVRreport = dao.selectList("help.listVRreport");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listVRreport;
	}

	@Override
	public void insertVRreport(ReplyReport dto) throws Exception {
		try {
			dao.insertData("help.insertVRreport", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
