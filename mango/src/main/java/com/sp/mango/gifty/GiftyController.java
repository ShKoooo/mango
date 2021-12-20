package com.sp.mango.gifty;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.mango.common.MyUtil;
import com.sp.mango.member.MemberSessionInfo;



@Controller("gifty.giftyController")
@RequestMapping("/gifty/*")
public class GiftyController {
	@Autowired
	private GiftyService service;
	
	@Autowired
	private MyUtil myUtil;
	
	@RequestMapping(value = "list")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "1") int group,
			HttpServletRequest req,
			Model model
			) throws Exception {
		
		List<Gifty> listGcategory = service.listGcategory();
		
		int dataCount;
		int rows = 6;
		int total_page = 0;
		
		Map<String, Object> map = new HashMap<String, Object>();
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		map.put("group", group);
		
		dataCount = service.dataCount(map);
		
		if(dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if(current_page > total_page) {
			current_page = total_page;
		}
		
		List<Gifty> list = service.listGifty(map);
		
		String cp = req.getContextPath();
		// String query = "rows=" + rows;
		// String listUrl = cp + "/gifty/list?group=" +group;
		String articleUrl = cp +"/gifty/article?group="+group+"&page=" + current_page;
		
		//if (query.length() != 0) {
		// listUrl += "?" + query;
		// articleUrl += "&" + query;
		//}
		
		model.addAttribute("listGcategory", listGcategory);
		model.addAttribute("list", list);
		model.addAttribute("articleUrl", articleUrl);
		model.addAttribute("total_page", total_page);
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("rows", rows);
		model.addAttribute("group", group);
		
		return ".gifty.list";
	}
	
	@RequestMapping(value = "morelist")
	@ResponseBody
	public Map<String, Object> moreList (@RequestParam(value = "pageNo", defaultValue = "2") int current_page) throws Exception {
		
		int rows = 10;
		int dataCount;
		
		Map<String, Object> map = new HashMap<String, Object>();
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		dataCount = service.dataCount(map);
		List<Gifty> list = service.listGifty(map);
		int total_page = myUtil.pageCount(rows, dataCount);
		if (current_page > total_page) {
			current_page = total_page;
		}
		
		Map<String, Object> model = new HashMap<>();
		
		model.put("dataCount", dataCount);
		model.put("total_page", total_page);
		model.put("page", current_page);
		
		model.put("list", list);
		
		return model;
	}
	

	
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String writeForm(Model model) throws Exception {
		List<Gifty> listGcategory = service.listGcategory();
		
		model.addAttribute("listGcategory", listGcategory);
		model.addAttribute("mode", "write");
		
		return ".gifty.write";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String writeSubmit(Gifty dto, HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			
			dto.setUserId(info.getUserId());
			service.insertGifty(dto);
			
		} catch (Exception e) {
		}
		
		return "redirect:/gifty/list";
	}
	
	@RequestMapping(value = "article", method = RequestMethod.GET)
	public String article(@RequestParam int gNum,
			@RequestParam String page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			
			Model model) throws Exception {
		
		keyword = URLDecoder.decode(keyword, "utf-8");

		String query = "page=" + page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + 
					"&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		service.updateHitCount(gNum);
		Gifty dto = service.readGifty(gNum);
		if(dto==null) {
			return "redirect:/gifty/list?" + query;
		}
		
		// dto.setgContent(myUtil.htmlSymbols(dto.getgContent()));
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		
		model.addAttribute("condition", condition);
		model.addAttribute("keyword", keyword);
		
		return ".gifty.article";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(@RequestParam int gNum,
			@RequestParam String page,
			HttpSession session,
			Model model) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		Gifty dto = service.readGifty(gNum);
		if(dto==null || !info.getUserId().equals(dto.getUserId())) {
			return "redirect:/gifty/list?page=" + page;
		}
		
		List<Gifty> listGcategory = service.listGcategory();
		
		model.addAttribute("listGcategory", listGcategory);
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		model.addAttribute("mode", "update");
		
		return ".gifty.write";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSubmit(Gifty dto,
			@RequestParam String page,
			HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			service.updateGifty(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/gifty/list?page=" + page;
	}
	
	@RequestMapping(value = "delete")
	public String deleteGifty(@RequestParam int gNum,
			@RequestParam String page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		keyword = URLDecoder.decode(keyword, "utf-8");
		String query = "page=" + page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		service.deleteGifty(gNum, info.getUserId());
		
		return "redirect:/gifyt/list?" + query;
	}
	
	@RequestMapping(value = "insertGwish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertGwish(@RequestParam int gNum,
			@RequestParam boolean userWished,
			HttpSession session) {
		String state = "true";
		int giftyWishCount = 0;
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("gNum", gNum);
		paramMap.put("userId", info.getUserId());
		
		try {
			if(userWished) {
				service.deleteGwish(paramMap);
			} else {
				service.insertGwish(paramMap);
			}
		} catch (DuplicateKeyException e) {
			state = "liked";
		} catch (Exception e) {
			state = "false";
		}
		
		giftyWishCount = service.giftyWishCount(gNum);
		
		Map<String, Object> model = new HashMap<>();
		model.put("state", state);
		model.put("giftyWishCount", giftyWishCount);
		
		return model;
		
	}
	
}
