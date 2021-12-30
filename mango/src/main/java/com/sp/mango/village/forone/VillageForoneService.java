package com.sp.mango.village.forone;

import java.util.List;
import java.util.Map;

public interface VillageForoneService {
	public void insertBoard(VillageForone dto) throws Exception;
	public List<VillageForone> memberListBoard(Map<String, Object> map);
	public int dataCount(Map<String, Object> map);
	public VillageForone readBoard(int vNum);
	public void updateHitCount(int vNum) throws Exception;
	public void updateBoard(VillageForone dto) throws Exception;
	public void deleteBoard(int vNum, String userId, int membership) throws Exception;
	
}
