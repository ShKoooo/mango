package com.sp.mango.gifty;

import java.util.List;
import java.util.Map;

public interface GiftyService {
	public void insertGifty(Gifty dto, String pathname) throws Exception;
	public List<Gifty> listGifty(Map<String, Object> map);
	public int dataCount(Map<String, Object> map);
	public Gifty readGifty(int gnum);
	public void updateHitCount(int gnum) throws Exception;
	public void updategifty(Gifty dto, String pathname) throws Exception;
	public void deletegifty(int gnum, String pahtname, String userId) throws Exception;

	public List<Gifty> listGcategory();
	
}
