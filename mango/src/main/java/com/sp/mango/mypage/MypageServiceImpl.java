package com.sp.mango.mypage;

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

}
