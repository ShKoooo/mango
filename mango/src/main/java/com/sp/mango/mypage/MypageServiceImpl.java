package com.sp.mango.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

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

}
