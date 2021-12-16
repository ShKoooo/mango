package com.sp.mango.village.qna;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

import com.sp.mango.common.MyUtil;
import com.sp.mango.member.MemberSessionInfo;

@Controller("village.qna.VillageQnaController")
@RequestMapping("/village/qna/*")
public class VillageQnaController {
	@Autowired
	private VillageQnaService service;
	@Autowired
	private MyUtil myUtil;
	
	
	@RequestMapping(value="list")
	public String list(@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpServletRequest req,
			Model model) throws Exception {
		
		String cp = req.getContextPath();
		
		int rows = 10;
		int total_page = 0;
		int dataCount = 0;
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}
		
		// 전체 페이지 수
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("keyword", keyword);
		
		dataCount = service.dataCount(map);
		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		// 다른 사람이 자료 삭제해서 전체 페이지 수 변화
		
		// 리스트에 출력할 데이터
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		// 글 리스트
		List<VillageQna> list = service.listBoard(map);
		
		// 리스트 번호
		int listNum, n = 0;
		for (VillageQna dto : list) {
			listNum = dataCount - (start + n - 1);
			dto.setListNum(listNum);
			n++;
		}
		
		String query = "";
		String listUrl = cp + "/village/qna/list";
		String articleUrl = cp + "/village/qna/article?page="+current_page;
		if(keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}
		
		if(query.length() != 0) {
			listUrl = cp + "/village/qna/list?"+query;
			articleUrl = cp + "/village/qna/article?page=" + current_page + "&" + query;
		}
		
		String paging = myUtil.paging(current_page, total_page, listUrl);
		
		model.addAttribute("list", list);
		model.addAttribute("articleUrl", articleUrl);
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		
		model.addAttribute("condition", condition);
		model.addAttribute("keyword", keyword);
		
		return ".village.qna.list";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String writeForm(Model model) throws Exception {
		
		model.addAttribute("mode", "write");
		return ".village.qna.write";
	}
	
	@RequestMapping(value = "/village/qna/write", method = RequestMethod.POST)
	public String writeSubmit(VillageQna dto, HttpSession session) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "qna";
		
		try {
			dto.setUserId(info.getUserId());
			service.insertBoard(dto, pathname);
		} catch (Exception e) {
		}
		
		return "redirect:/village/qna/list";
	}
	
	@RequestMapping(value = "article")
	public String article(@RequestParam int num,
			@RequestParam String page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpSession session,
			Model model) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		keyword = URLDecoder.decode(keyword, "utf-8");
		
		String query = "page=" + page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + 
					"&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		service.updateHitCount(num);
	
		// 가져오기
		VillageQna dto = service.readBoard(num);
		if (dto == null) {
			return "redirect:/village/qna/list?" + query;
		}
		
		dto.setContent(dto.getContent());
		
		// 이전글, 다음글
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("keyword", keyword);
		map.put("vNum", num);
		
		VillageQna preReadDto = service.preReadBoard(map);
		VillageQna nextReadDto = service.nextReadBoard(map);
		
		model.addAttribute("dto", dto);
		model.addAttribute("preReadDto", preReadDto);
		model.addAttribute("nextReadDto", nextReadDto);
		
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		
		return ".village.qna.article";
	}
	
}
