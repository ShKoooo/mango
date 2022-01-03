package com.sp.mango.admin.village;

import java.util.List;
import java.util.Map;

public interface VillageReportService {
	public int countReport(String brType);
	public List<VillageReport> listReport(Map<String, Object> map);
	public void updateVbbsReportChecked(int reportNum);
	public void updateVbbsReplyReportChecked(int reportNum);
}
