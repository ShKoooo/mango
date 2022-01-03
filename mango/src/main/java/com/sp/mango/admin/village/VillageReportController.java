package com.sp.mango.admin.village;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sp.mango.common.MyUtil;

@Controller("admin.village.villageReportController")
@RequestMapping(value="/admin/village/*")
public class VillageReportController {
	@Autowired
	private VillageReportService service;
	@Autowired
	private MyUtil myUtil;
	
	@RequestMapping("list")
	public String list(
			Model model,
			HttpServletRequest req,
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(defaultValue = "all") String brType
			) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String cp = req.getContextPath();
		
		int rows = 10;
		int total_page = 0;
		int dataCount = 0;
		
		dataCount = service.countReport(brType);
		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if (total_page < current_page) {
			current_page = total_page;
		}
		
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		
		map.put("start", start);
		map.put("end", end);
		map.put("brType", brType);
		
		List<VillageReport> list = service.listReport(map);
		
		for (VillageReport dto : list) {
			/*
				if (dto.getRcontent() != null && !dto.getRcontent().equals("")) {
					dto.setRcontent(dto.getRcontent().replaceAll("\n", "<br>"));				
				}
			*/
			
			if (dto.getSubject() != null && !dto.getSubject().equals("")) {
				if (dto.getSubject().length() > 12) {
					dto.setSubject(dto.getSubject().substring(0,12)+"...");
				}
			}
			
			if (dto.getFullSubject() != null && !dto.getFullSubject().equals("")) {
				if (dto.getFullSubject().length() > 100) {
					dto.setFullSubject(dto.getFullSubject().substring(0,100)+"...");
				}
			}
		}
		
		String query = "brType="+brType;
		String listUrl = cp + "/admin/village/list";
		listUrl += "?"+query;
		String paging = myUtil.paging(current_page, total_page, listUrl);
		
		
		
		model.addAttribute("list",list);
		
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		model.addAttribute("brType", brType);
		
		return ".admin.village.list";
	}
	
	@RequestMapping("clear")
	public String clear(
			final RedirectAttributes reAttr,
			HttpServletRequest req,
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam String brType,
			@RequestParam String reportNum,
			@RequestParam(defaultValue="all") String selBrType
			) {
		
		if (brType.equals("bbs")) {
			service.updateVbbsReportChecked(Integer.parseInt(reportNum));
		} else if (brType.equals("reply")) {
			service.updateVbbsReplyReportChecked(Integer.parseInt(reportNum));
		}
		
		reAttr.addAttribute("page",current_page);
		reAttr.addAttribute("brType",selBrType);
		
		return "redirect:/admin/village/list";
	}
}
