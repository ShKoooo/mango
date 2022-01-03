package com.sp.mango.admin.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

@Service("admin.product.productReportService")
public class ProductReportServiceImpl implements ProductReportService {
	@Autowired
	private CommonDAO dao;

	@Override
	public int countReport() {
		int result = 0;
		
		try {
			result = dao.selectOne("adminProduct.countReport");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<ProductReport> listReport(Map<String, Object> map) {
		List<ProductReport> list = null;
		
		try {
			list = dao.selectList("adminProduct.listReport",map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void updateClear(int reportNum) throws Exception {
		try {
			dao.updateData("adminProduct.updateClear",reportNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
}
