package com.sp.mango.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

@Service("member.memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired
	private CommonDAO dao;
	
	@Override
	public Member loginMember(String userId) {
		Member dto = null;
		
		try {
			dto = dao.selectOne("member.loginMember",userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

}
