package com.sp.mango.admin.cscenter;

import java.io.File;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.mango.common.FileManager;
import com.sp.mango.common.MyUtil;
import com.sp.mango.member.MemberSessionInfo;

@Controller("admin.cscenter.inquiryController")
@RequestMapping("admin/cscenter/*")
public class InquiryController {
	
	@Autowired
	private InquiryService service;
	
	@Autowired
	private MyUtil myUtil;
	
	@Autowired
	private FileManager fileManager;
	
	@RequestMapping(value = "list")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue="") String state,
			HttpServletRequest req,
			Model model
			) throws Exception {
		
		String cp = req.getContextPath();

		int rows = 10; // 한 화면에 보여주는 게시물 수
		int total_page = 0;
		int dataCount = 0;

		if (req.getMethod().equalsIgnoreCase("GET")) { // GET 방식인 경우
			keyword = URLDecoder.decode(keyword, "utf-8");
		}

		// 전체 페이지 수
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("keyword", keyword);
		map.put("state", state);

		dataCount = service.dataCount(map);
		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}

		// 다른 사람이 자료를 삭제하여 전체 페이지수가 변화 된 경우
		if (total_page < current_page) {
			current_page = total_page;
		}

		// 리스트에 출력할 데이터를 가져오기
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);

		// 글 리스트
		List<Inquiry> list = service.listBoard(map);

		// 리스트의 번호
		int listNum, n = 0;
		for (Inquiry dto : list) {
			listNum = dataCount - (start + n - 1);
			dto.setListNum(listNum);
			n++;
		}

		String query = "";
		String listUrl = cp + "admin/cscenter/list";
		String articleUrl = cp + "/admin/cscenter/article?" + "&page=" + current_page;
		if (keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}
		
		if(state.length()!=0) {
        	if(query.length()!=0)
        		query = query +"&state="+state;
        	else
        		query = "state="+state;
        }

		if (query.length() != 0) {
			listUrl += "&" + query;
			articleUrl += "&" + query;
		}

		String paging = myUtil.paging(current_page, total_page, listUrl);


		model.addAttribute("list", list);
		model.addAttribute("articleUrl", articleUrl);
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		model.addAttribute("state", state);

		model.addAttribute("condition", condition);
		model.addAttribute("keyword", keyword);

		
		
		return ".admin.cscenter.list";
	}
	
	@RequestMapping(value="detaile")
	public String detaileMember(
			@RequestParam int inquiryNum,
			Model model) throws Exception {
		
		Inquiry dto=service.readBoard(inquiryNum);

		model.addAttribute("dto", dto);
		
		return "admin/cscenter/detaile";
	}
	
	@RequestMapping(value="update", method = RequestMethod.POST)
	public String update(
			@RequestParam int inquiryNum,
			@RequestParam String userId,
			@RequestParam String state,
			Model model) throws Exception {
		
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("inquiryNum", inquiryNum);
			map.put("userId", userId);
			map.put("state", state);
			
			service.updateBoard(map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/admin/cscenter/list";
	}
	
	@RequestMapping(value = "download")
	public void download(@RequestParam int inquiryNum, 
			HttpServletRequest req, HttpServletResponse resp,
			HttpSession session) throws Exception {

		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "inquiry";

		Inquiry dto = service.readBoard(inquiryNum);

		if (dto != null) {
			boolean b = fileManager.doFileDownload(dto.getSaveFilename(), 
					dto.getOriginalFilename(), pathname, resp);
			if (b) {
				return;
			}
		}

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print("<script>alert('파일 다운로드가 실패 했습니다.');history.back();</script>");
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(
			@RequestParam int inquiryNum,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		String check = "false";
		
		if(info.getMembership() > 50) {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("inquiryNum", inquiryNum);
				
				service.deleteBoard(map);
				
				check = "true";
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("check", check);
		
		return model;
	
	}

}
