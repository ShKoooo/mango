package com.sp.mango.greview;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

@Service("greview.greviewService")
public class GreviewServiceImpl implements GreviewService {

	@Autowired
	private CommonDAO dao;

	@Override
	public void insertGreview(Greview dto) throws Exception {
		try {
			// 시퀀스?
			
			dao.insertData("greview.insertGreview", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Greview> listGreview(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Greview readGreview(int gNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteGreview(int gNum, String buyerId) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}
