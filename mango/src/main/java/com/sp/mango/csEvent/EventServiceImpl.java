package com.sp.mango.csEvent;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

@Service("csEvent.eventService")
public class EventServiceImpl implements EventService {
	
	@Autowired
	private CommonDAO dao;

	@Override
	public void insertEvent(Event dto) throws Exception {
		try {
			dao.insertData("event.insertEvent", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Event> listEvent(Map<String, Object> map) {
		
		List<Event> list = null;
		
		try {
			list = dao.selectList("event.listEvent", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("event.dataCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Event readEvent(int num) {
		Event dto = null;
		
		try {
			dto = dao.selectOne("event.readEvent", num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@Override
	public void updateEvent(Event dto) throws Exception {
		try {
			dao.updateData("event.updateEvent", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void deleteEvent(Map<String, Object> map) throws Exception {
		try {
			dao.selectOne("event.deleteEvent", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Event> listCategory(Map<String, Object> map) {
		List<Event> listCategory = null;
		try {
			listCategory = dao.selectList("event.listCategory", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listCategory;
	}

}
