package com.sp.mango.admin.gifty;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

@Service("admin.gifty.giftyReportService")
public class GiftyReportServiceImpl implements GiftyReportService {
	
	@Autowired
	private CommonDAO dao;
	
	@Override
	public int countReport() {
		int result = 0;
		
		try {
			result = dao.selectOne("adminGifty.countReport");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<GiftyReport> listReport(Map<String, Object> map) {
		List<GiftyReport> list = null;
		
		try {
			list = dao.selectList("adminGifty.listReport",map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void updateClear(int reportNum) throws Exception {
		try {
			dao.updateData("adminGifty.updateClear",reportNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
