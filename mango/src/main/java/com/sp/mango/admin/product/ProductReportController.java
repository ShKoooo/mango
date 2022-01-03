package com.sp.mango.admin.product;

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

@Controller("admin.product.productReportController")
@RequestMapping(value="/admin/product/*")
public class ProductReportController {
	@Autowired
	private ProductReportService service;
	@Autowired
	private MyUtil myUtil;
	
	@RequestMapping("list")
	public String list (
			Model model,
			HttpServletRequest req,
			@RequestParam(value="page", defaultValue="1") int current_page
			) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String cp = req.getContextPath();
		
		int rows = 10;
		int total_page = 0;
		int dataCount = 0;
		
		dataCount = service.countReport();
		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		if (total_page<1) total_page=1; 
		
		if (total_page < current_page) {
			current_page = total_page;
		}
		
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		
		map.put("start", start);
		map.put("end", end);
		
		List<ProductReport> list = service.listReport(map);
		
		for (ProductReport dto : list) {
			if (dto.getSubject() != null && !dto.getSubject().equals("")) {
				if (dto.getSubject().length() > 12) {
					dto.setSubject(dto.getSubject().substring(0,12)+"...");
				}
			}
		}
		
		String listUrl = cp + "/admin/gifty/list";
		String paging = myUtil.paging(current_page, total_page, listUrl);
		
		model.addAttribute("list",list);
		model.addAttribute("page",current_page);
		model.addAttribute("dataCount",dataCount);
		model.addAttribute("total_page",total_page);
		model.addAttribute("paging",paging);
		
		return ".admin.product.list";
	}
	
	@RequestMapping("clear")
	public String clear(
			final RedirectAttributes reAttr,
			@RequestParam int reportNum,
			@RequestParam(value="page", defaultValue="1") int current_page
			) throws Exception {
		
		try {
			service.updateClear(reportNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		reAttr.addAttribute("page",current_page);
		return "redirect:/admin/product/list";
	}
}
