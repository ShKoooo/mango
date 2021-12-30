package com.sp.mango.admin.cscenter;

import java.util.List;
import java.util.Map;

public interface InquiryService {
	public List<Inquiry> listBoard(Map<String, Object> map);
	public int dataCount(Map<String, Object> map);
	
	public Inquiry readBoard(int num);
	
	public Inquiry readBoard2(String userId);
	
	public void updateBoard(Map<String, Object> map) throws Exception;
	public void deleteBoard(Map<String, Object> map) throws Exception;
	
	public Inquiry readMemberState(String userId);
	
}
