package com.sp.mango.csQna;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

@Service("csQna.qnaService")
public class QnaServiceImpl implements QnaService {
	
	@Autowired
	private CommonDAO dao;

	@Override
	public void insertFaq(Qna dto) throws Exception {
		try {
			dao.insertData("faq.insertQna", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Qna> listFaq(Map<String, Object> map) {
		
		List<Qna> list = null;
		
		try {
			list = dao.selectList("faq.listFaq", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("faq.dataCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Qna readFaq(int faqNum) {
		Qna dto = null;
		
		try {
			dto = dao.selectOne("faq.readFaq", faqNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@Override
	public void updateFaq(Qna dto) throws Exception {
		try {
			dao.updateData("faq.updateFaq", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void deleteFqa(Map<String, Object> map) throws Exception {
		try {
			dao.selectOne("faq.deleteFaq", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Qna> listCategory(Map<String, Object> map) {
		List<Qna> listCategory = null;
		try {
			listCategory = dao.selectList("faq.listCategory", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listCategory;
	}

}
