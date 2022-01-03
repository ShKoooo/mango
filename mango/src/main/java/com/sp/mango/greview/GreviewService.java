package com.sp.mango.greview;

import java.util.List;
import java.util.Map;

public interface GreviewService {
	public void insertGreview(Greview dto) throws Exception;
	public List<Greview> listGreview(Map<String, Object> map);
	public int dataCount(Map<String, Object> map);
	public Greview readGreview(int gNum);
	public void deleteGreview(int gNum, String buyerId) throws Exception;
}
