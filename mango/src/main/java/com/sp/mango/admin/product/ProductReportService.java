package com.sp.mango.admin.product;

import java.util.List;
import java.util.Map;

public interface ProductReportService {
	public int countReport();
	public List<ProductReport> listReport(Map<String, Object> map);
	public void updateClear(int reportNum) throws Exception;
}
