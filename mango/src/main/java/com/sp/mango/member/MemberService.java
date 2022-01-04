package com.sp.mango.member;

import java.util.Map;

public interface MemberService {
	public Member loginMember(String userId);
	public Integer countMemberByParam(Map<String, Object> map);
	public Member readMember(String userId);
	public void insertMember(Member dto, String pathname) throws Exception;
	public void updateMember(Member dto, String pathname) throws Exception;
	
	public int countMemberAddr(String userId) throws Exception;
	public Integer readAreaByBcode(String bcodeCut) throws Exception;
	public int getAreaSeqNum() throws Exception;
	public void insertArea(MemberAddr dto) throws Exception;
	public void insertMemberAddr(MemberAddr dto) throws Exception;
	public void deleteMemberAddr(Integer maNum) throws Exception;
	
	public Integer countBusnByParam(Map<String, Object> map);
	public void insertBusiness(Business dto, String pathname) throws Exception;
	public void insertArea2(Business dto) throws Exception;
	public void updateBusiness(Business dto, String pathname) throws Exception;
	public void deleteBusiness(String userId, String pathname) throws Exception;
	
	public Integer selectLoginFail(String userId);
	public void updateDefaultLoginFail(String userId) throws Exception;
	public void updateLoginFail(String userId) throws Exception;
	
	public Integer selectEnable(String userId);
	public void updateDefaultEnable(String userId) throws Exception;
	public void updateEnable(Map<String,Object> map) throws Exception;
	public void insertMemberState(Map<String, Object> map) throws Exception;
	
	public void deleteMember(String userId) throws Exception;
}
