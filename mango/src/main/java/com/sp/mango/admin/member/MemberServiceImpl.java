package com.sp.mango.admin.member;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

@Service("admin.member.memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private CommonDAO dao;
	
	@Override
	public List<Member> list(Map<String, Object> map) throws Exception {
		List<Member> list = null;
		
		try {
			list = dao.selectList("adminMember.listMember",map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	@Override
	public int countMember(Map<String, Object> map) throws Exception {
		int result = 0;
		
		try {
			result = dao.selectOne("adminMember.countMember",map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}

	@Override
	public MemberID readMember(String memberId) {
		MemberID dto = null;
		
		try {
			dto = dao.selectOne("adminMember.readMember",memberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@Override
	public List<MemberAddr> listMemberAddr(String memberId) {
		List<MemberAddr> list = null;
		
		try {
			list = dao.selectList("adminMember.listMemberAddr",memberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ReportMember> listReportMember(String memberId) {
		List<ReportMember> list = null;
		
		try {
			list = dao.selectList("adminMember.listReportMember",memberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<MemberState> listMemberState(Map<String,Object> map) {
		List<MemberState> list = null;
		
		try {
			list = dao.selectList("adminMember.listMemberState",map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public int countMemberState(String memberId) {
		int result = 0;
		
		try {
			result = dao.selectOne("adminMember.countMemberState",memberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<MemberStateCode> listMemberStateCode() {
		List<MemberStateCode> list = null;
		
		try {
			list = dao.selectList("adminMember.listMemberStateCode");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void insertMemberState(Map<String, Object> stateMap) throws Exception {
		try {
			dao.insertData("adminMember.insertMemberState",stateMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void updateMemberEnable(Map<String, Object> enableMap) throws Exception {
		try {
			dao.insertData("adminMember.updateMemberEnable",enableMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void updateMemberLoginFail(String memberId) throws Exception {
		try {
			dao.insertData("adminMember.updateMemberLoginFail",memberId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void updateMannerMinusDeg(Map<String, Object> degMap) throws Exception {
		try {
			dao.insertData("adminMember.updateMannerMinusDeg",degMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void updateReportChecked(int repMemNum) throws Exception {
		try {
			dao.updateData("adminMember.updateReportChecked",repMemNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
