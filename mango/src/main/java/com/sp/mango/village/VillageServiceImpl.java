package com.sp.mango.village;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;
import com.sp.mango.village.qna.VillageQna;

@Service("village.VillageService")
public class VillageServiceImpl implements VillageService {
	@Autowired
	private CommonDAO dao;
	
	@Override
	public List<VillageQna> listQna(Map<String, Object> map) {
		List<VillageQna> listqna = null;
		
		try {
			listqna = dao.selectList("qna.listBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listqna;
	}

}
