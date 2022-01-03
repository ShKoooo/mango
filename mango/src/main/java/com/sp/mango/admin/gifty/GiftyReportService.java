package com.sp.mango.admin.gifty;

import java.util.List;
import java.util.Map;

public interface GiftyReportService {
	public int countReport();
	public List<GiftyReport> listReport(Map<String, Object> map);
	public void updateClear(int reportNum) throws Exception;
}
