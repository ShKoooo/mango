package com.sp.mango.home;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

@Service("home.HomeService")
public class HomeServiceImpl implements HomeService {
	@Autowired
	private CommonDAO dao;
	
	@Override
	public List<Home> productPopularList() {
		List<Home> productPopularList = null;
		
		try {
			productPopularList = dao.selectList("home.productPop");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return productPopularList;
	}

	@Override
	public List<Home> giftyPopularList() {
		List<Home> giftyPopularList = null;
		
		try {
			giftyPopularList = dao.selectList("home.giftyPop");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return giftyPopularList;
	}

	@Override
	public List<Home> villagePopularList() {
		List<Home> villagePopularList = null;
		
		try {
			villagePopularList = dao.selectList("home.villagePop");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return villagePopularList;
	}

}
