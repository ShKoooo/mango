package com.sp.mango.village.forone;

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

@Controller("village.forone.VillageForoneController")
@RequestMapping("/village/forone/*")
public class VillageForoneController {

	@Autowired
	private VillageForoneService service;
	@Autowired
	private MyUtil myUtil;
	
	
	@RequestMapping(value = "list")
	public String list(@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpServletRequest req,
			Model model) throws Exception {
		
		HttpSession session = req.getSession();
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		int rows = 8;
		int total_page = 0;
		int dataCount = 0;
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}
		
		// 전체 페이지 수
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("keyword", keyword);
		map.put("info", info!=null);
		
		dataCount = service.dataCount(map);
		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		// 다른 사람이 게시글 삭제하면 전체 페이지 변환하는 부분
		if(total_page < current_page) {
			current_page = total_page;
		}
		
		// 리스트에 출력할 데이터
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		// 글 리스트
		List<VillageForone> list = null;
		
		if (info != null) {
			list = service.memberListBoard(map);
		}
		
		// ckeditor에서 첨부한 이미지를 썸네일로 불러오기
		for(VillageForone dto : list) {
			List<String> imgs = myUtil.getImgSrc(dto.getContent());
			
			if(imgs != null && imgs.size() > 0) {
				dto.setThumbnail(imgs.get(0));
			} else {
				dto.setThumbnail(cp + "/resources/images/nothumb.png");
			}
		}
		
		// 리스트 번호
		int listNum, n = 0;
		for (VillageForone dto : list) {
			listNum = dataCount - (start + n - 1);
			dto.setListNum(listNum);
			n++;
		}
		
		String query = "";
		String listUrl = cp + "/village/forone/list";
		String articleUrl = cp + "/village/forone/article?page="+current_page;
		if (keyword.length()!=0) {
			query = "condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		if(query.length() != 0) {
			listUrl = cp + "/village/forone/list?"+query;
			articleUrl = cp + "/village/forone/article?page="+current_page + "&" + query;
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
		
		return ".village.forone.list";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String writeForm(Model model, HttpSession session) throws Exception {
		
		model.addAttribute("mode", "write");
		
		return ".village.forone.write";
	}
	
	@RequestMapping(value = "village/forone/write", method = RequestMethod.POST)
	public String writeSubmit(VillageForone dto, HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			service.insertBoard(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/village/forone/list";
	}
	
	@RequestMapping(value = "article", method = RequestMethod.GET)
	public String article(@RequestParam int vNum,
			@RequestParam String page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpSession session,
			Model model) throws Exception {
		
		keyword = URLDecoder.decode(keyword, "utf-8");
		
		String query = "page=" + page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + 
					"&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		service.updateHitCount(vNum);
		
		VillageForone dto = service.readBoard(vNum);
		if(dto == null) {
			return "redirect:/village/forone/list?" + query;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("keyword", keyword);
		map.put("vNum", vNum);
		
		
		
		model.addAttribute("dto", dto);

		model.addAttribute("page", page);
		model.addAttribute("query", query);
		
		return ".village.forone.article";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(@RequestParam int vNum,
			@RequestParam String page,
			HttpSession session,
			Model model) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		VillageForone dto = service.readBoard(vNum);
		if(dto == null || ! info.getUserId().equals(dto.getUserId())) {
			return "redirect:/village/forone/list?page=" + page;
		}
		
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		model.addAttribute("mode", "update");
		
		return ".village.forone.write";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSubmit(VillageForone dto,
			@RequestParam String page,
			HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			service.updateBoard(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/village/forone/list?page="+page;
	}
	
	@RequestMapping(value = "delete")
	public String delete(@RequestParam int vNum,
			@RequestParam String page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpSession session) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		keyword = URLDecoder.decode(keyword, "utf-8");
		String query = "page="+page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition +"&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		service.deleteBoard(vNum, info.getUserId(), info.getMembership());
		
		return "redirect:/village/forone/list?"+query;
	}
	
}
