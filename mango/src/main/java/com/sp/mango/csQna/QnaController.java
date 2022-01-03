package com.sp.mango.csQna;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.mango.common.MyUtil;
import com.sp.mango.member.MemberSessionInfo;

@Controller("csQna.qnaController")
@RequestMapping("/csQna/*")
public class QnaController {
	
	@Autowired
	private QnaService service;
	
	@Autowired
	private MyUtil myUtil;
	
	
	@RequestMapping(value = "qna")
	public String main(
			@RequestParam(value = "pageNo", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "") String search,
			Model model
			) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Qna> listCategory = service.listCategory(map);
		
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("categoryNum", 0);
		model.addAttribute("pageNo", current_page);
		model.addAttribute("search", search);
		
		
		return ".csQna.qna";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String writeForm(Model model) throws Exception {

		
		Map<String, Object> map = new HashMap<>();
		List<Qna> listCategory = service.listCategory(map);

		// map.put("mode", "all");
		// List<Faq> listAllCategory=service.listCategory(map);

		model.addAttribute("pageNo", "1");
		model.addAttribute("mode", "write");
		model.addAttribute("listCategory", listCategory);
		// model.addAttribute("listAllCategory", listAllCategory);

		return ".csQna.write";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String writeSubmit(Qna dto, HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			
			if(info.getMembership() > 50) {
				dto.setUserId(info.getUserId());
				service.insertFaq(dto);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/csQna/qna?pageNo=1";
	}
	
	
	@RequestMapping(value = "list")
	public String list(
			@RequestParam(value = "pageNo", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "0") int categoryNum,
			HttpServletRequest req,
			@RequestParam(defaultValue = "") String search,
			Model model
			) throws Exception {
		
		int rows = 10;
		int total_page = 0;
		int dataCount = 0;
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			search = URLDecoder.decode(search, "utf-8");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
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
		
		List<Qna> list = service.listFaq(map);
		int listNum, n = 0;
		for (Qna dto : list) {
			listNum = dataCount - (start + n - 1);
			dto.setListNum(listNum);
			n++;

			// dto.setFaqContent(myUtil.htmlSymbols(dto.getFaqContent()));
		}
		
		if(search.length() != 0) {
			search = URLEncoder.encode(search, "utf-8");
		}
		
		String paging = myUtil.pagingMethod(current_page, total_page, "listPage");
		
		model.addAttribute("list", list);
		model.addAttribute("pageNo", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		model.addAttribute("categoryNum", categoryNum);
		model.addAttribute("search", search);
		
		return "csQna/list";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(
			@RequestParam int faqNum,
			@RequestParam String pageNo,
			HttpSession session,
			Model model
			) throws Exception {
		
		Qna dto = service.readFaq(faqNum);
		if(dto == null) {
			return "redirect:/csQna/qna?pageNo=" + pageNo;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Qna> listCategory = service.listCategory(map);
		
		model.addAttribute("mode", "update");
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("dto", dto);
		model.addAttribute("listCategory", listCategory);
		
		
		return ".csQna.write";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSubmit(Qna dto,
			@RequestParam String pageNo,
			HttpSession session) throws Exception {

		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");

		try {
			dto.setUserId(info.getUserId());
			service.updateFaq(dto);
		} catch (Exception e) {
		}

		return "redirect:/csQna/qna?pageNo=" + pageNo;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(
			@RequestParam int faqNum,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		if(info.getMembership() > 50) {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("faqNum", faqNum);
				
				service.deleteFqa(map);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	
	}
	


}
