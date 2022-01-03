package com.sp.mango.admin.village;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

@Service("admin.village.villageReportService")
public class VillageReportServiceImpl implements VillageReportService {
	@Autowired
	private CommonDAO dao;

	@Override
	public int countReport(String brType) {
		int result = 0;
		
		try {
			result = dao.selectOne("adminVillage.countReport",brType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<VillageReport> listReport(Map<String, Object> map) {
		List<VillageReport> list = null;
		
		try {
			list = dao.selectList("adminVillage.listReport",map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void updateVbbsReportChecked(int reportNum) {
		try {
			dao.updateData("adminVillage.updateVbbsReportChecked",reportNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateVbbsReplyReportChecked(int reportNum) {
		try {
			dao.updateData("adminVillage.updateVbbsReplyReportChecked",reportNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
