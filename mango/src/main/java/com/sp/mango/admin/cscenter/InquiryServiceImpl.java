package com.sp.mango.admin.cscenter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.sp.mango.common.FileManager;
import com.sp.mango.common.dao.CommonDAO;


@Service("admin.cscenter.inquiryService")
public class InquiryServiceImpl implements InquiryService {
	
	@Autowired
	private CommonDAO dao;
	
	@Autowired
	private FileManager fileManager;

	@Override
	public List<Inquiry> listBoard(Map<String, Object> map) {
		
		List<Inquiry> list = null;
		
		try {
			list = dao.selectList("inquiry.listBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("inquiry.dataCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Inquiry readBoard(int num) {
		Inquiry dto = null;
		
		try {
			dto = dao.selectOne("inquiry.readBoard", num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@Override
	public void updateBoard(Map<String, Object> map) throws Exception {
		try {
			dao.updateData("inquiry.updateBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void deleteBoard(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("inquiry.deleteBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}


	@Override
	public Inquiry readBoard2(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inquiry readMemberState(String userId) {
		Inquiry dto = null;
		
		try {
			dto = dao.selectOne("inquiry.readMemberState", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}


		

}
