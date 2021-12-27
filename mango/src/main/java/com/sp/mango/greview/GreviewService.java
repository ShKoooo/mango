package com.sp.mango.greview;

import java.util.List;
import java.util.Map;

public interface GreviewService {
	public void insertGreview(Greview dto) throws Exception;
	public List<Greview> listGreview(Map<String, Object> map); // 상훈님?
	public int dataCount(Map<String, Object> map); // 상훈님?
	public Greview readGreview(int gNum); // 상훈님?
	public void deleteGreview(int gNum, String buyerId) throws Exception; // 상훈님?
}
