package com.sp.mango.member;

import java.util.Map;

public interface MemberService {
	public Member loginMember(String userId);
	public Integer countMemberByParam(Map<String, Object> map);
	public Member readMember(String userId);
	public void insertMember(Member dto) throws Exception;
	public void updateMember(Member dto) throws Exception;
	
	public int countMemberAddr(String userId) throws Exception;
	public Integer readAreaByBcode(String bcodeCut) throws Exception;
	public int getAreaSeqNum() throws Exception;
	public void insertArea(MemberAddr dto) throws Exception;
	public void insertMemberAddr(MemberAddr dto) throws Exception;
	public void deleteMemberAddr(Integer maNum) throws Exception;
}
