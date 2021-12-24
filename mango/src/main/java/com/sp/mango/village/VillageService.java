package com.sp.mango.village;

import java.util.List;
import java.util.Map;

import com.sp.mango.village.qna.VillageQna;

public interface VillageService {
	public List<VillageQna> listQna(Map<String, Object> map);
	}
