package com.sp.mango.member;

import java.util.Map;

public interface MemberService {
	public Member loginMember(String userId);
	public Integer countMemberByParam(Map<String, Object> map);
	public Member readMember(String userId);
}
