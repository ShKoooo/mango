package com.sp.mango.village.forone;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

@Service("village.forone.villageForoneService")
public class VillageForoneServiceImpl implements VillageForoneService {
	
	@Autowired
	private CommonDAO dao;
	
	@Override
	public void insertBoard(VillageForone dto) throws Exception {
		try {
			dao.insertData("forone.insertBoard", dto);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public List<VillageForone> memberListBoard(Map<String, Object> map) {
		List<VillageForone> memberList = null;
		
		try {
			memberList = dao.selectList("forone.memberListBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberList;
	}
	
	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("forone.dataCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public VillageForone readBoard(int vNum) {
		VillageForone dto = null;
		
		try {
			dto = dao.selectOne("forone.readBoard", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	@Override
	public void updateHitCount(int vNum) throws Exception {
		try {
			dao.updateData("forone.updateHitCount", vNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public void updateBoard(VillageForone dto) throws Exception {
		try {
			dao.updateData("forone.updateBoard", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public void deleteBoard(int vNum, String userId, int membership) throws Exception {
		try {
			VillageForone dto = readBoard(vNum);
			if(dto == null || (membership < 51 && ! dto.getUserId().equals(userId))) {
				return;
			}
			dao.deleteData("forone.deleteBoard", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
