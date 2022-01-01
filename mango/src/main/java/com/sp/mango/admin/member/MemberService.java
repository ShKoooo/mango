package com.sp.mango.admin.member;

import java.util.List;
import java.util.Map;

public interface MemberService {
	public List<Member> list (Map<String, Object> map) throws Exception;
	public int countMember (Map<String, Object> map) throws Exception;
	
	public MemberID readMember (String memberId);
	public List<MemberAddr> listMemberAddr (String memberId);
	public List<ReportMember> listReportMember (String memberId);
	public List<MemberState> listMemberState (Map<String,Object> map);
	
	public int countMemberState (String memberId);
	public List<MemberStateCode> listMemberStateCode();
	
	public void insertMemberState (Map<String,Object> stateMap) throws Exception;
	public void updateMemberEnable (Map<String, Object> enableMap) throws Exception;
	public void updateMemberLoginFail (String memberId) throws Exception;
	public void updateMannerMinusDeg (Map<String, Object> degMap) throws Exception;
	public void updateReportChecked (int repMemNum) throws Exception;
}
