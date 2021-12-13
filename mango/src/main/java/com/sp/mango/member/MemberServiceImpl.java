package com.sp.mango.member;

import java.util.Map;

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

	@Override
	public Integer countMemberByParam(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("member.userDuplCheck",map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Member readMember(String userId) {
		Member dto = null;
		
		try {
			dto = dao.selectOne("member.readMember",userId);
		} catch (Exception e) {
		}
		
		return dto;
	}

	@Override
	public void insertMember(Member dto) throws Exception {
		try {
			dao.insertData("member.insertMember",dto);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updateMember(Member dto) throws Exception {
		try {
			dao.updateData("member.updateMember",dto);
		} catch (Exception e) {
		}
	}

}
