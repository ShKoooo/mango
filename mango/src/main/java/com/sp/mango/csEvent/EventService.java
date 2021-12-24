package com.sp.mango.csEvent;

import java.util.List;
import java.util.Map;

public interface EventService {
	
	public void insertEvent(Event dto) throws Exception;
	public List<Event> listEvent(Map<String, Object> map);
	public int dataCount(Map<String, Object> map);
	public Event readEvent(int num);
	public void updateEvent(Event dto) throws Exception;
	public void deleteEvent(Map<String, Object> map) throws Exception;
	
	public List<Event> listCategory(Map<String, Object> map);

}
