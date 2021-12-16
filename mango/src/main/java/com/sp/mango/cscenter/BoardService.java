package com.sp.mango.cscenter;

import java.util.List;
import java.util.Map;

public interface BoardService {
	
	public List<Board> listBoard(Map<String, Object> map);
	public int dataCount();
	public Board readBoard(int num);
	
	public Board preReadBoard(Map<String, Object> map);
	public Board nextReadBoard(Map<String, Object> map);
	
	public void insertBoard(Board dto, String pathname) throws Exception;
	public void updateBoard(Board dto, String pathname) throws Exception;
	public void deleteBoard(int num, String pathname, String userId) throws Exception;
	

}
