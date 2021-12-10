package com.sp.mango.gifty;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

@Service("gifty.giftyService")
public class GiftyServiceImpl implements GiftyService {

	@Autowired
	private CommonDAO dao;
	
	@Override
	public void insertgifty(Gifty dto, String pathname) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Gifty> listGifty(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Gifty readgifty(int gnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateHitCount(int gnum) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updategifty(Gifty dto, String pathname) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletegifty(int gnum, String pahtname, String userId) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
