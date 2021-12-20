package com.sp.mango.gifty;

import java.util.List;
import java.util.Map;

public interface GiftyService {
	public void insertGifty(Gifty dto) throws Exception;
	public List<Gifty> listGifty(Map<String, Object> map);
	public int dataCount(Map<String, Object> map);
	public Gifty readGifty(int gnum);
	public void updateHitCount(int gnum) throws Exception;
	public void updateGifty(Gifty dto) throws Exception;
	public void deleteGifty(int gnum, String userId) throws Exception;

	public List<Gifty> listGcategory();
	
	public void insertGwish(Map<String, Object> map) throws Exception;
	public Map<String, Object> listGwish(Map<String, Object> map);
	public int gwishCount(int gNum);
	public int giftyWishCount(int gNum);
	public void deleteGwish(Map<String, Object> map) throws Exception;
	public boolean userGwished(Map<String, Object> map);
	
}
