package com.sp.mango.csEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.mango.common.MyUtil;
import com.sp.mango.member.MemberSessionInfo;

@Controller("csEvent.eventController")
@RequestMapping("/csEvent/*")
public class EventController {
	
	@Autowired
	private EventService service;
	
	@Autowired
	private MyUtil myUtil;
	
	
	@RequestMapping(value = "event")
	public String main(Model model) throws Exception {
		
		return ".csEvent.event";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String writeForm(Model model) throws Exception {

		model.addAttribute("pageNo", "1");
		model.addAttribute("mode", "write");

		return ".csEvent.write";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String writeSubmit(Event dto, HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			
			if(info.getMembership() > 50) {
				dto.setUserId(info.getUserId());
				service.insertEvent(dto);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/csEvent/event?pageNo=1";
	}
	
	
	@RequestMapping(value = "list")
	public String list(
			@RequestParam(value = "pageNo", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "0") int categoryNum,
			HttpServletRequest req,
			Model model
			) throws Exception {
		
		int rows = 10;
		int total_page = 0;
		int dataCount = 0;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryNum", categoryNum);
		
		dataCount = service.dataCount(map);
		if(dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if (total_page < current_page) {
			current_page = total_page;
		}
		
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		List<Event> list = service.listEvent(map);
		
		
		int listNum, n = 0;
		for (Event dto : list) {
			listNum = dataCount - (start + n - 1);
			dto.setListNum(listNum);

			// dto.setFaqContent(myUtil.htmlSymbols(dto.getFaqContent()));
		}
		
		String paging = myUtil.pagingMethod(current_page, total_page, "listPage");
		
		model.addAttribute("list", list);
		model.addAttribute("pageNo", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		model.addAttribute("categoryNum", categoryNum);
		
		return "csEvent/list";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(
			@RequestParam int num,
			@RequestParam String pageNo,
			HttpSession session,
			Model model
			) throws Exception {
		
		Event dto = service.readEvent(num);
		if(dto == null) {
			return "redirect:/csEvent/event?pageNo=" + pageNo;
		}
		
		
		model.addAttribute("mode", "update");
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("dto", dto);
		
		
		return ".csEvent.write";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSubmit(
			Event dto,
			@RequestParam int num
			) throws Exception {
		
		try {
			service.updateEvent(dto);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return ".csEvent.event";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(
			@RequestParam int num,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		if(info.getMembership() > 50) {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("num", num);
				
				service.deleteEvent(map);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	
	}
	

}
