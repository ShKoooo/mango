package com.sp.mango.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.FileManager;
import com.sp.mango.common.dao.CommonDAO;

@Service("member.memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired
	private CommonDAO dao;
	@Autowired
	private FileManager fileManager;
	
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
	public void insertMember(Member dto, String pathname) throws Exception {
		try {
			String saveFilename = fileManager.doFileUpload(dto.getProfileImg(), pathname);
			
			if (saveFilename != null) {
				dto.setUserImgSaveFileName(saveFilename);
			}
			
			dao.insertData("member.insertMember",dto);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updateMember(Member dto, String pathname) throws Exception {
		try {
			String saveFilename = fileManager.doFileUpload(dto.getProfileImg(), pathname);
			
			if (saveFilename != null) {
				if (dto.getUserImgSaveFileName().length() != 0) {
					fileManager.doFileDelete(dto.getUserImgSaveFileName(),pathname);
				}
				
				dto.setUserImgSaveFileName(saveFilename);
			}
			
			dao.updateData("member.updateMember",dto);
		} catch (Exception e) {
		}
	}

	@Override
	public void insertMemberAddr(MemberAddr dto) throws Exception {
		try {
			dao.insertData("member.insertMemberAddr",dto);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int countMemberAddr(String userId) throws Exception {
		int result = 0;
		
		try {
			result = dao.selectOne("member.countAddrById",userId);
		} catch (Exception e) {
			throw e;
		}
		
		return result;
	}

	@Override
	public Integer readAreaByBcode(String bcodeCut) throws Exception {
		Integer result = null;
		
		try {
			result = dao.selectOne("member.readAreaByBcode",bcodeCut);
		} catch (Exception e) {
			throw e;
		}
		
		return result;
	}

	@Override
	public void insertArea(MemberAddr dto) throws Exception {
		try {
			dao.insertData("member.insertArea",dto);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int getAreaSeqNum() throws Exception {
		int result = 0;
		
		try {
			result = dao.selectOne("member.getAreaSeqNum");
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return result;
	}

	@Override
	public void deleteMemberAddr(Integer maNum) throws Exception {
		try {
			dao.deleteData("member.deleteMemberAddr",maNum);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public Integer countBusnByParam(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("member.busnDuplCheck",map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void insertBusiness(Business dto, String pathname) throws Exception {
		try {
			String saveFilename = fileManager.doFileUpload(dto.getProfileImg(), pathname);
			
			if (saveFilename != null) {
				dto.setBusImgSaveFileName(saveFilename);
			}
			
			dao.insertData("member.insertBusiness",dto);
		} catch (Exception e) {
		}
	}

	@Override
	public void insertArea2(Business dto) throws Exception {
		try {
			dao.insertData("member.insertArea2",dto);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updateBusiness(Business dto, String pathname) throws Exception {
		try {
			String saveFilename = fileManager.doFileUpload(dto.getProfileImg(), pathname);
			// System.out.println(":::: profileImg : "+dto.getProfileImg().toString());
			
			if (saveFilename != null) {
				if (dto.getBusImgSaveFileName().length() != 0) {
					fileManager.doFileDelete(dto.getBusImgSaveFileName(),pathname);
				}
				
				dto.setBusImgSaveFileName(saveFilename);
			}
			
			dao.updateData("member.updateBusiness",dto);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteBusiness(String userId, String pathname) throws Exception {
		try {
			if (pathname != null) {
				fileManager.doFileDelete(pathname);
			}
			
			dao.deleteData("member.deleteBusiness",userId);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	@Override
	public Integer selectLoginFail(String userId) {
		Integer result = null;
		
		try {
			result = dao.selectOne("member.selectLoginFail",userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void updateDefaultLoginFail(String userId) throws Exception {
		try {
			dao.updateData("member.updateDefaultLoginFail",userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void updateLoginFail(String userId) throws Exception {
		try {
			dao.updateData("member.updateLoginFail",userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Integer selectEnable(String userId) {
		Integer result = null;
		
		try {
			result = dao.selectOne("member.selectEnable",userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void updateDefaultEnable(String userId) throws Exception {
		try {
			dao.updateData("member.updateDefaultEnable",userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void updateEnable(Map<String,Object> map) throws Exception {
		try {
			dao.updateData("member.updateEnable",map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void insertMemberState(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("member.insertMemberState",map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteMember(String userId) throws Exception {
		try {
			dao.deleteData("member.deleteMember",userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}
