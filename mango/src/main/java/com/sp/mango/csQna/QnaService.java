package com.sp.mango.csQna;

import java.util.List;
import java.util.Map;

public interface QnaService {
	
	public void insertFaq(Qna dto) throws Exception;
	public List<Qna> listFaq(Map<String, Object> map);
	public int dataCount(Map<String, Object> map);
	public Qna readFaq(int faqNum);
	public void updateFaq(Qna dto) throws Exception;
	public void deleteFqa(Map<String, Object> map) throws Exception;
	
	public List<Qna> listCategory(Map<String, Object> map);

}
