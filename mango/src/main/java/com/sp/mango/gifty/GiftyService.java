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
	
}
