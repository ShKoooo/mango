package com.sp.mango.cscenter;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.sp.mango.common.FileManager;
import com.sp.mango.common.MyUtil;
import com.sp.mango.member.MemberSessionInfo;

@Controller("cscenter.cscenterController")
@RequestMapping("/cscenter/*")
public class CscenterController {
	
	@Autowired
	private BoardService service;
	
	@Autowired
	private MyUtil myUtil;
	
	@Autowired
	private FileManager fileManager;
	
	
	@RequestMapping(value = "list")
	public String list() throws Exception {
		
		return ".cscenter.list";
	}
	
	@RequestMapping(value = "csNotice")
	public String csNotice(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			HttpServletRequest req,
			Model model
			) throws Exception {
		// 공지사항 글 리스트
		
		String cp = req.getContextPath();
		
		int rows = 10;
		int total_page = 0;
		int dataCount = 0;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		dataCount = service.dataCount();
		if(dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if(total_page < current_page) {
			current_page = total_page;
		}
		
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		List<Board> list = service.listBoard(map);
		
		Date endDate = new Date();
		long gap;
		
		int listNum, n = 0;
		for (Board dto : list) {
			listNum = dataCount - (start + n - 1);
			dto.setListNum(listNum);
	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date beginDate = formatter.parse(dto.getnRegDate());
			
			
			// 날짜차이(일)
			gap=(endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
			dto.setGap(gap);
			
			/*
			// 날짜차이(시간)
			gap = (endDate.getTime() - beginDate.getTime()) / (60 * 60 * 1000);
			dto.setGap(gap);
			*/

			dto.setnRegDate(dto.getnRegDate().substring(0, 10));

			n++;
		}
		
		
		String listUrl = cp + "/cscenter/csNotice";
		String articleUrl = cp + "/cscenter/article?page=" + current_page;
		
		String paging = myUtil.paging(current_page, total_page, listUrl);
		
		model.addAttribute("list", list);
		model.addAttribute("articleUrl", articleUrl);
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);

		
		return ".cscenter.csNotice";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String writeForm(
			HttpSession session,
			Model model) throws Exception {
		
		model.addAttribute("mode", "write");
		
		return ".cscenter.write";
	}
	
	@RequestMapping(value = "cscenter/write", method = RequestMethod.POST)
	public String writeSubmit(
			Board dto,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "cscenter";
		
		
		try {
			dto.setUserId(info.getUserId());
			dto.setUserName(info.getUserName());
			service.insertBoard(dto, pathname);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/cscenter/csNotice";
	}
	
	
	@RequestMapping(value = "article")
	public String article(
			@RequestParam int noticeNum,
			@RequestParam String page,
			HttpSession session,
			Model model
			) throws Exception {
		
		String query = "page=" + page;
		
		Board dto = service.readBoard(noticeNum);
		if(dto == null) {
			return "redirect:/cscenter/csNotice?" + query;
		}
		
		// dto.setnContent(myUtil.htmlSymbols(dto.getnContent()));
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		
		return ".cscenter.article";
	}
	
	
	@RequestMapping(value = "download")
	public void download(
			@RequestParam int noticeNum,
			HttpServletRequest req,
			HttpServletResponse resp,
			HttpSession session
			) throws Exception {
		
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "cscenter";
		
		Board dto = service.readBoard(noticeNum);
		
		if(dto != null) {
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
	
	@RequestMapping(value = "delete")
	public String delete(
			@RequestParam int noticeNum,
			@RequestParam String page,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		String query = "page=" + page;
		
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "cscenter";
		
		service.deleteBoard(noticeNum, pathname, info.getUserId());
		
		
		
		return "redirect:/cscenter/csNotice?" + query;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(
			@RequestParam int noticeNum,
			@RequestParam String page,
			HttpSession session,
			Model model
			) throws Exception {
		
		// MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		Board dto = service.readBoard(noticeNum);
		if(dto == null) {
			return "redirect:/cscenter/csNotice?page=" + page;
		}
		
		model.addAttribute("dto", dto);
		model.addAttribute("mode", "update");
		model.addAttribute("page", page);
		
		return ".cscenter.write";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSubmit(
			Board dto,
			@RequestParam String page,
			HttpSession session
			) throws Exception {
		
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "cscenter";
		
		try {
			service.updateBoard(dto, pathname);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return "redirect:/cscenter/csNotice?page=" + page;
	}
	
	
	@RequestMapping(value = "deleteFile")
	public String deleteFile(
			@RequestParam int noticeNum,
			@RequestParam String page,
			HttpSession session
			) throws Exception {
		
		
		
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "cscenter";
		
		Board dto = service.readBoard(noticeNum);
		
		try {
			if (dto.getSaveFilename() != null) {
				fileManager.doFileDelete(dto.getSaveFilename(), pathname); // 실제파일삭제
				dto.setSaveFilename("");
				dto.setOriginalFilename("");
				service.updateBoard(dto, pathname); // DB 테이블의 파일명 변경(삭제)
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return "redirect:/cscenter/update?noticeNum=" + noticeNum + "&page=" +page;
	}
	
}
