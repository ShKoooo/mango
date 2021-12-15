package com.sp.mango.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;
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

}
