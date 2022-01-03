package com.sp.mango.search;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.mango.common.MyUtil;
import com.sp.mango.member.MemberSessionInfo;
import com.sp.mango.product.MemberAddr;
import com.sp.mango.product.Product;
import com.sp.mango.product.ProductService;

@Controller("search.searchController")
@RequestMapping("/search/*")
public class SearchController {
	@Autowired
	private ProductService service;
	
	@Autowired
	private MyUtil myUtil;
	
	@RequestMapping("productList")
	public String productList(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(value = "maLat", defaultValue = "0") double maLat,
			@RequestParam(value = "maLon", defaultValue = "0") double maLon,
			@RequestParam(value = "pcNum", defaultValue = "0") int pcNum,
			@RequestParam(value = "opt", defaultValue = "0") int opt,
			@RequestParam(value = "searchKeyword", defaultValue = "") String searchKeyword,
			@RequestParam(value = "isKeyword", defaultValue = "0") String isKeyword,
			HttpServletRequest req,
			Model model
			) throws Exception {
	
		HttpSession session = req.getSession();
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		int rows = 6; // 한 화면에 보여주는 게시물 수
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			searchKeyword = URLDecoder.decode(searchKeyword, "utf-8");
		}
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("info", info!=null);
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		
		map.put("userId", info!=null ? info.getUserId() : "");
		map.put("searchKeyword", searchKeyword);
		
		List<MemberAddr> listMemberAddr = null;
		int memAddrCount = 0;
		
		String userId = "";
		if (info != null) {
			userId = info.getUserId();
			map.put("membership", info.getMembership());
			listMemberAddr = service.listMemberAddr(info.getUserId());
			memAddrCount = service.memAddrCount(info.getUserId());
			
			if(listMemberAddr.size() > 0 && maLat == 0 && maLon == 0) { 
				map.put("maLat", listMemberAddr.get(0).getaLat());
				map.put("maLon", listMemberAddr.get(0).getaLon());
				maLat = listMemberAddr.get(0).getaLat();
				maLon = listMemberAddr.get(0).getaLon();
			}
		}		
		map.put("pcNum", pcNum);
		map.put("userId", userId);
		map.put("searchKeyword", searchKeyword);
		map.put("isKeyword", isKeyword);
		
		int dataCount = service.dataCount(map);
		int total_page = 0;

		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		// 다른 사람이 자료를 삭제하여 전체 페이지수가 변화 된 경우
		if (total_page < current_page) {
			current_page = total_page;
		}
		
		// 리스트에 출력할 데이터 가져오기
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		
		
		// 리스트
		List<Product> list = service.memberListProduct(map);
		
		if(info != null) { 
			if(opt == 0 && listMemberAddr.size() > 0) {
				opt = listMemberAddr.get(0).getAreaNum();
			}
		}
		
		// 썸내일 사진
	    for(Product dto : list) {
	        List<String> imgs = myUtil.getImgSrc(dto.getpContent());
	        if(imgs != null && imgs.size() > 0) {
	           dto.setpImgSaveFileName(imgs.get(0));
	        } else {
	           dto.setpImgSaveFileName(cp+"/resources/images/noimage.png");
	        }
	    }

		String query = "&searchKeyword=" + URLEncoder.encode(searchKeyword, "utf-8");
		String articleUrl = cp + "/product/article?pcNum="+pcNum+"&page="+current_page+query;
				
		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("memAddrCount", memAddrCount);

		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("page", current_page);
		model.addAttribute("list", list);
		model.addAttribute("pcNum", pcNum);
		model.addAttribute("maLat", maLat);
		model.addAttribute("maLon", maLon);
		
		
		model.addAttribute("articleUrl", articleUrl);
		
		List<Product> listCategory = service.listCategory();
		
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("opt", opt);
		
		model.addAttribute("searchKeyword", searchKeyword);
		
		return ".search.list";
	}
	
	// AJAX - Map을 JSON으로 변환 반환
	@RequestMapping(value = "morelist")
	@ResponseBody
	public Map<String, Object> morelist(
			@RequestParam(value = "page", defaultValue = "2") int current_page,
			@RequestParam(value = "maLat", defaultValue = "0") double maLat,
			@RequestParam(value = "maLon", defaultValue = "0") double maLon,
			@RequestParam(value = "pcNum", defaultValue = "0") int pcNum,
			@RequestParam(value = "opt", defaultValue = "0") int opt,
			@RequestParam(value = "searchKeyword", defaultValue = "") String searchKeyword,
			@RequestParam(value = "isKeyword", defaultValue = "0") String isKeyword,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		int rows = 6;
		int dataCount;
		
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<MemberAddr> listMemberAddr = null;
		
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		
		map.put("searchKeyword", searchKeyword);
		
		String userId = "";
		if (info != null) {
			userId = info.getUserId();
			map.put("membership", info.getMembership());
			listMemberAddr = service.listMemberAddr(info.getUserId());
		}		
		map.put("pcNum", pcNum);
		map.put("userId", userId);

		dataCount = service.dataCount(map);
		int total_page = myUtil.pageCount(rows, dataCount);
		if (current_page > total_page) {
			current_page = total_page;
		}
		
		// 리스트
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		map.put("searchKeyword", searchKeyword);
		map.put("isKeyword", isKeyword);
				
		List<Product> list = service.memberListProduct(map);
		if(info != null) { 
			if(opt == 0 && listMemberAddr.size() > 0) {
				opt = listMemberAddr.get(0).getAreaNum();
			}
		}
		
		Map<String, Object> model = new HashMap<>();
		
		model.put("dataCount", dataCount);
		model.put("total_page", total_page);
		model.put("page", current_page);
		model.put("pcNum", pcNum);
		model.put("list", list);
		
		return model;
	}	
}
