package com.sp.mango.mypage;

import java.util.List;

import com.sp.mango.member.Business;
import com.sp.mango.member.MemberAddr;

public interface MypageService {
	public MannerProfile readMannerProfile(String userId) throws Exception;
	public void insertMannerProfile(String userId) throws Exception;
	public List<MemberAddr> listMemberAddr(String userId) throws Exception;
	public Integer businessDuplCheck(String userId) throws Exception;
	public Business readBusiness(String userId) throws Exception;
}
