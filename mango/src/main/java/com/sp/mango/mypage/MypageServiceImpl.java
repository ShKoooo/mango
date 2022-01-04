package com.sp.mango.mypage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;
import com.sp.mango.member.Business;
import com.sp.mango.member.MemberAddr;

@Service("mypage.mypageService")
public class MypageServiceImpl implements MypageService {
	@Autowired	
	private CommonDAO dao;
	
	@Override
	public MannerProfile readMannerProfile(String userId) throws Exception {
		MannerProfile dto = null;
		
		try {
			dto = dao.selectOne("mypage.getMannerProfile", userId);
		} catch (Exception e) {
			throw e;
		}
		
		return dto;
	}
	
	@Override
	public void insertMannerProfile(String userId) throws Exception {
		try {
			dao.insertData("mypage.insertMannerProfile",userId);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public List<MemberAddr> listMemberAddr(String userId) throws Exception {
		List<MemberAddr> list = null;
		
		try {
			list = dao.selectList("mypage.listAddr", userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	@Override
	public Integer businessDuplCheck(String userId) throws Exception {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			map.put("chkWay", "userId");
			map.put("userParam", userId);
			
			result = dao.selectOne("member.busnDuplCheck",map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}

	@Override
	public Business readBusiness(String userId) throws Exception {
		Business dto = null;
		
		try {
			dto = dao.selectOne("mypage.readBusiness",userId);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return dto;
	}

	@Override
	public List<PickedUser> listPickedUser(String userId) throws Exception {
		List<PickedUser> list = null;
		
		try {
			list = dao.selectList("mypage.listPickedUser",userId);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return list;
	}

	@Override
	public List<BlockedUser> listBlockedUser(String userId) throws Exception {
		List<BlockedUser> list = null;
		
		try {
			list = dao.selectList("mypage.listBlockedUser",userId);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return list;
	}

	@Override
	public List<Keyword> listKeyword(String userId) throws Exception {
		List<Keyword> list = null;
		
		try {
			list = dao.selectList("mypage.listKeyword",userId);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return list;
	}

	@Override
	public int myPickCount(Map<String, Object> map) throws Exception {
		int result = 0;
		
		try {
			result = dao.selectOne("mypage.myPickCount", map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return result;
	}

	@Override
	public List<PickedUser> listMyPickedUser(Map<String, Object> map) throws Exception {
		List<PickedUser> list = null;
		
		try {
			list = dao.selectList("mypage.listMyPickedUser",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return list;
	}

	@Override
	public void deleteMySelection(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("mypage.deleteMySelection",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public String readUserIdByNickName(String userNickName) throws Exception {
		String userId = null;
		
		try {
			userId = dao.selectOne("mypage.readUserIdByNickName",userNickName);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return userId;
	}

	@Override
	public void insertPickedUser(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("mypage.insertPickedUser",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public int myBlockCount(Map<String, Object> map) throws Exception {
		int result = 0;
		try {
			result = dao.selectOne("mypage.myBlockCount",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		return result;
	}

	@Override
	public List<BlockedUser> listMyBlockedUser(Map<String, Object> map) throws Exception {
		List<BlockedUser> list = null;
		
		try {
			list = dao.selectList("mypage.listMyBlockedUser",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return list;
	}

	@Override
	public void insertBlockedUser(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("mypage.insertBlockedUser",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public int myKeywordCount(Map<String, Object> map) throws Exception {
		int result = 0;
		
		try {
			result = dao.selectOne("mypage.myKeywordCount",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return result;
	}

	@Override
	public List<Keyword> listMyKeyword(Map<String, Object> map) throws Exception {
		List<Keyword> list = null;
		
		try {
			list = dao.selectList("mypage.listMyKeyword",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return list;
	}

	@Override
	public void insertKeyword(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("mypage.insertKeyword",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public List<Note> listNoteSender(Map<String,Object> map) throws Exception {
		List<Note> list = null;
		
		try {
			list = dao.selectList("mypage.listNoteSender",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return list;
	}

	@Override
	public List<Note> listNoteReceiver(Map<String,Object> map) throws Exception {
		List<Note> list = null;
		
		try {
			list = dao.selectList("mypage.listNoteReceiver",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return list;
	}

	@Override
	public Note readNoteLastTime(Map<String,Object> map) throws Exception {
		Note returnDto = null;
		
		try {
			returnDto = dao.selectOne("mypage.readNoteLastTime",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return returnDto;
	}

	@Override
	public List<Note> listNoteNote(Map<String, Object> map) throws Exception {
		List<Note> list = null;
				
		try {
			list = dao.selectList("mypage.listNoteNote",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}		
		
		return list;
	}

	@Override
	public void insertNote(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("mypage.insertNote",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public int getBlockCount(String userId) throws Exception {
		int result = 0;
		
		try {
			result = dao.selectOne("mypage.getBlockCount",userId);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return result;
	}

	@Override
	public void updateNoteReadDate(Map<String, Object> map) throws Exception {
		try {
			dao.updateData("mypage.updateNoteReadDate",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public void updateSendDelete(Map<String, Object> map) throws Exception {
		try {
			dao.updateData("mypage.updateSendDelete",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public void updateReceiveDelete(Map<String, Object> map) throws Exception {
		try {
			dao.updateData("mypage.updateReceiveDelete",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public void updateUserAllDelete(Map<String, Object> map) throws Exception {
		try {
			dao.updateData("mypage.updateUserAllDelete",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public void updateEntireMyNoteDelete(Map<String, Object> map) throws Exception {
		try {
			dao.updateData("mypage.updateEntireMyNoteDelete",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public List<Rating> listRating(Map<String,Object> map) throws Exception {
		List<Rating> list = null;
		
		try {
			list = dao.selectList("mypage.listRating",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return list;
	}

	@Override
	public List<Rating> listProductRating(Map<String,Object> map) throws Exception {
		List<Rating> list = null;
		
		try {
			list = dao.selectList("mypage.listProductRating",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}		
		
		return list;
	}

	@Override
	public List<Rating> listGiftyRating(Map<String,Object> map) throws Exception {
		List<Rating> list = null;
		
		try {
			list = dao.selectList("mypage.listGiftyRating",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}		
		
		return list;
	}

	@Override
	public int countRating(String userId) throws Exception {
		int result = 0;
		
		List<Integer> list = null;
		try {
			list = dao.selectList("mypage.countRating",userId);
			
			for (Integer val : list) {
				if (val != null) {
					result += val;
					System.out.println(":::: val : "+val);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return result;
	}

	@Override
	public int countProductRating(String userId) throws Exception {
		int result = 0;
		
		try {
			result = dao.selectOne("mypage.countProductRating",userId);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return result;
	}

	@Override
	public int countGiftyRating(String userId) throws Exception {
		int result = 0;
		
		try {
			result = dao.selectOne("mypage.countGiftyRating",userId);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return result;
	}

	@Override
	public int countActivity(Map<String, Object> map) throws Exception {
		int result = 0;
		List<Integer> list = new ArrayList<Integer>();
		
		try {
			list = dao.selectList("mypage.countActivity",map);
			
			for (Integer val : list) {
				if (val != null) {
					result += val;
					System.out.println(":::: val : "+val);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return result;
	}

	@Override
	public List<Activity> listActivity(Map<String, Object> map) throws Exception {
		List<Activity> list = null;
		
		try {
			list = dao.selectList("mypage.listActivity",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return list;
	}

	@Override
	public int countAccount(Map<String, Object> map) throws Exception {
		int result = 0;
		
		try {
			result = dao.selectOne("mypage.countAccount",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return result;
	}

	@Override
	public List<Account> listAccount(Map<String, Object> map) throws Exception {
		List<Account> list = null;
		
		try {
			list = dao.selectList("mypage.listAccount",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return list;
	}

	@Override
	public List<RepMemberReason> listRepMemReason() throws Exception {
		List<RepMemberReason> list = null;
		
		try {
			list = dao.selectList("mypage.listRepMemReason");
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
				
		return list;
	}

	@Override
	public void insertReportMember(Map<String,Object> map) throws Exception {
		try {
			dao.insertData("mypage.insertReportMember",map);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public int countNotReadNote(Map<String, Object> countNoteMap) {
		int result = 0;
		
		try {
			result = dao.selectOne("mypage.countNotReadNote", countNoteMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int countNotReadEachNote(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("mypage.countNotReadEachNote", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
