package com.sp.mango.gifty;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.sp.mango.mypage.Note;



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
			@RequestParam(defaultValue = "0") int group,
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
	public Map<String, Object> moreList (
			@RequestParam(value = "page", defaultValue = "2") int current_page,
			@RequestParam(defaultValue = "0") int group
		) throws Exception {
		
		int rows = 6;
		int dataCount;
		
		Map<String, Object> map = new HashMap<String, Object>();
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		map.put("group", group);
		
		dataCount = service.dataCount(map);
		int total_page = myUtil.pageCount(rows, dataCount);
		if (current_page > total_page) {
			current_page = total_page;
		}
		List<Gifty> list = service.listGifty(map);
		
		Map<String, Object> model = new HashMap<>();
		
		model.put("dataCount", dataCount);
		model.put("total_page", total_page);
		model.put("page", current_page);
		model.put("group", group);
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
			@RequestParam(defaultValue = "0") int group,
			HttpSession session,
			Model model) throws Exception {
		
		keyword = URLDecoder.decode(keyword, "utf-8");

		String query = "page=" + page + "&group=" + group;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + 
					"&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		service.updateHitCount(gNum);
		Gifty dto = service.readGifty(gNum);
		if(dto==null) {
			return "redirect:/gifty/list?" + query;
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gNum", gNum);
		
		boolean userGwished = false;
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		List<GiftyReport> listGreport = null;
		
		if(info != null) {
			map.put("userId", info.getUserId());
			userGwished = service.userGwished(map);
			
			listGreport = service.listGreport();
		}
		
		int giftyWishCount = service.giftyWishCount(gNum);
		String gUpOkDate = service.gUpOkDate(gNum);
		// dto.setgContent(myUtil.htmlSymbols(dto.getgContent()));
		/*
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long gap;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date regDate = sdf.parse(dto.getgRegdate());
		gap = (cal.after(regDate)) / (1000*60*60*24);
		
		model.addAttribute("gap", gap);
		*/
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		model.addAttribute("group", group);
		model.addAttribute("userGwished", userGwished);
		model.addAttribute("giftyWishCount", giftyWishCount);
		model.addAttribute("listGreport", listGreport);
		model.addAttribute("condition", condition);
		model.addAttribute("keyword", keyword);
		model.addAttribute("gUpOkDate", gUpOkDate);
		
		return ".gifty.article";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(@RequestParam int gNum,
			@RequestParam String page,
			@RequestParam int group,
			HttpSession session,
			Model model) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		Gifty dto = service.readGifty(gNum);
		if(dto==null || !info.getUserId().equals(dto.getUserId())) {
			return "redirect:/gifty/list?group="+ group+ "&page=" + page;
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
			@RequestParam int group,
			HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			if(dto.getgStatus().equals("거래완료")) {
				service.updateSdate(dto);
			} else {
				service.updateGifty(dto);
			}
			
		} catch (Exception e) {
		}
		
		return "redirect:/gifty/list?group= "+ group +"&page=" + page;
	}
	
	@RequestMapping(value = "delete")
	public String deleteGifty(@RequestParam int gNum,
			@RequestParam String page,
			@RequestParam int group,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		keyword = URLDecoder.decode(keyword, "utf-8");
		String query = "page=" + page + "&group=" + group;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		
		service.deleteGifty(gNum, info.getUserId());
		
		return "redirect:/gifty/list?" + query;
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
			state = "wished";
		} catch (Exception e) {
			state = "false";
		}
		
		giftyWishCount = service.giftyWishCount(gNum);
		
		Map<String, Object> model = new HashMap<>();
		model.put("state", state);
		model.put("giftyWishCount", giftyWishCount);
		
		return model;
		
	}
	
	@RequestMapping(value = "report")
	public String reportSubmit(
			GiftyReport dto,
			@RequestParam int gNum,
			@RequestParam String page,
			@RequestParam int group,
			HttpSession session
			) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			
			service.insertGreport(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/gifty/article?group=" + group +"&page=" + page + "&gNum=" + gNum; 
	}
	
	@RequestMapping("updateDate")
	public String updateDate(
			Gifty dto,
			@RequestParam int gNum,
			HttpServletResponse resp,
			HttpSession session
			) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			/*
			Date date = new Date();
			long gap;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date regDate = sdf.parse(dto.getgRegdate());
			gap = (date.getTime() - regDate.getTime()) / (1000*60*60);
			
			if(gap < 3) {
				resp.setContentType("text/html; charset=utf-8");
				PrintWriter out = resp.getWriter();
				out.print("<script>alert('끌어올리기는 첫 등록 3일 후 가능합니다');</script>");
			} else {
			}
				*/
			service.updateDate(gNum, info.getUserId());
			
		} catch (Exception e) {
		}
		
		return "redirect:/gifty/list"; 
	}
	
	
	@RequestMapping("poplist")
	public String poplist() throws Exception {
		
		return ".gifty.poplist";
	}
	
	@RequestMapping("sendMsg")
	public String sendMsg(
			Note dto,
			@RequestParam int gNum,
			HttpSession session) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");	
		
		try {
			dto.setSendId(info.getUserId());
			
			Gifty vo = service.readGifty(gNum);
			String nContent = "안녕하세요 "+vo.getgSubject()+"를 구매하고 싶어서 쪽지남깁니다.";
			dto.setReceiveId(vo.getUserId());
			dto.setNoteContent(nContent);
		
			service.sendMsg(dto);
		} catch (Exception e) {

		}
		
		return ".mypage.note";
	}
	
}
