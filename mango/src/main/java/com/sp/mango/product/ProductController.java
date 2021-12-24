package com.sp.mango.product;

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

import com.mongodb.DuplicateKeyException;
import com.sp.mango.common.MyUtil;
import com.sp.mango.member.MemberSessionInfo;

@Controller("product.productController")
@RequestMapping("/product/*")
public class ProductController {
	@Autowired
	private ProductService service;
	
	@Autowired
	private MyUtil myUtil;	
	
	// 게시글 리스트
	@RequestMapping(value = "list")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(value = "maLat", defaultValue = "0") double maLat,
			@RequestParam(value = "maLon", defaultValue = "0") double maLon,
			@RequestParam(value = "pcNum", defaultValue = "0") int pcNum,
			@RequestParam(value = "opt", defaultValue = "0") int opt,
			HttpServletRequest req,
			Model model
			) throws Exception {
		
		HttpSession session = req.getSession();
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		int rows = 6; // 한 화면에 보여주는 게시물 수
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("info", info!=null);
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		
		List<MemberAddr> listMemberAddr = null;
		int memAddrCount = 0;
		
		if (info != null) {
			map.put("membership", info.getMembership());
			listMemberAddr = service.listMemberAddr(info.getUserId());
			memAddrCount = service.memAddrCount(info.getUserId());
			
			if(listMemberAddr.size() > 0 && maLat == 0 && maLon == 0) { 
				map.put("maLat", listMemberAddr.get(0).getaLat());
				map.put("maLon", listMemberAddr.get(0).getaLon());
			}
		}		
		map.put("pcNum", pcNum);
		
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

		// String listUrl = cp + "/product/list";
		String articleUrl = cp + "/product/article?pcNum="+pcNum+"&page="+current_page;
				
		// String paging = myUtil.paging(current_page, total_page, listUrl);
		
		
		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("memAddrCount", memAddrCount);

		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("page", current_page);
		model.addAttribute("list", list);
		model.addAttribute("pcNum", pcNum);
		
		// model.addAttribute("paging", paging);
		model.addAttribute("articleUrl", articleUrl);
		
		List<Product> listCategory = service.listCategory();
		
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("opt", opt);

		return ".product.list";
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
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		int rows = 6;
		int dataCount;
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<MemberAddr> listMemberAddr = null;
		
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		
		if (info != null) {
			map.put("membership", info.getMembership());
			listMemberAddr = service.listMemberAddr(info.getUserId());
		}		
		map.put("pcNum", pcNum);

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
	
	// 게시글 수정
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String writeForm(Model model, HttpSession session) throws Exception {
		List<Product> listCategory = service.listCategory();

		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		List<MemberAddr> listMemberAddr = service.listMemberAddr(info.getUserId());
		
		
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("mode", "write");
		
		return ".product.write";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String writeSubmit(Product dto, HttpSession session) throws Exception {
//		String root = session.getServletContext().getRealPath("/");
//		String path = root + "uploads" + File.separator + "product";
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			
			service.insertProduct(dto);
		} catch (Exception e) {
		}
		
		
		return "redirect:/product/list";
	}
	
	// 게시글 보기
	@RequestMapping(value = "article")
	public String article(
			@RequestParam int pNum,
			@RequestParam String page,
			@RequestParam int pcNum,
			@RequestParam(value = "maLat", defaultValue = "0") double maLat,
			@RequestParam(value = "maLon", defaultValue = "0") double maLon,
			@RequestParam(value = "opt", defaultValue = "0") int opt,
			HttpSession session,
			Model model
			) throws Exception {
		
		String query = "page=" + page + "&pcNum=" + pcNum;
		
		service.updateHitCount(pNum);
		
		// 글보기
		Product dto = service.readProduct(pNum);
		if(dto == null) {
			return "redirect:/product/list?" + query;
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pNum", pNum);
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		
		// 게시글 관심 여부
		boolean userProductWished = false;
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		List<ProductReport> listPreport = null;
		
		if(info != null) {
			map.put("userId", info.getUserId());
			userProductWished = service.userProductWished(map);
			
			listPreport = service.listPreport();
			
			query = "page=" + page + "&pcNum=" + pcNum 
						+ "&maLat=" + maLat + "&maLon=" + maLon + "&opt=" + opt;
		}
		
		int productWishCount = service.productWishCount(pNum);
		String pUpOkDate = service.pUpOkDate(pNum);
		
		model.addAttribute("dto", dto);
		model.addAttribute("userProductWished", userProductWished);
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		model.addAttribute("productWishCount", productWishCount);
		model.addAttribute("listPreport", listPreport);
		model.addAttribute("pcNum", pcNum);
		model.addAttribute("pUpOkDate", pUpOkDate);
		
		return ".product.article";
	}
	
	// 게시글 수정
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(
			@RequestParam int pNum,
			@RequestParam String page,
			@RequestParam int pcNum,
			HttpSession session,
			Model model) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		List<Product> listCategory = service.listCategory();
		List<MemberAddr> listMemberAddr = service.listMemberAddr(info.getUserId());
		
		Product dto = service.readProduct(pNum);
		if(dto == null || !info.getUserId().equals(dto.getUserId())) {
			return "redirect:/product/list?pcNum=" + pcNum + "&page=" + page;
		}
		
		model.addAttribute("dto", dto);
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("mode", "update");
		model.addAttribute("page", page);
		model.addAttribute("pcNum", pcNum);
		
		return ".product.write";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSubmit(Product dto,
			@RequestParam String page,
			@RequestParam int pcNum,
			@RequestParam String soldDateTF,
			HttpSession session
			) throws Exception {

		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			if(soldDateTF.equals("거래완료")) {
				service.updateSoldDate(dto);
			}
			
			service.updateProduct(dto);
		} catch (Exception e) {
		}

		return "redirect:/product/list?pcNum="+ pcNum + "&page=" + page;
	}
	
	
	// 게시글 관심 추가/삭제 : AJAX - JSON
	@RequestMapping(value = "insertProductWish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertProductWish(
			@RequestParam int pNum,
			@RequestParam boolean userWished,
			HttpSession session
			) {
		
		String state = "true";
		int productWishCount = 0;
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
			
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pNum", pNum);
		paramMap.put("userId", info.getUserId());
		
		try {
			if(userWished) {
				service.deleteProductWish(paramMap);
			} else {
				service.insertProductWish(paramMap);
			}
			
		} catch (DuplicateKeyException e) {
			state = "wished";
		} catch (Exception e) {
			state = "false";
		}
		
		productWishCount = service.productWishCount(pNum);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("state", state);
		model.put("productWishCount", productWishCount);

		return model;
	}
	
	//게시글 삭제
	@RequestMapping(value = "delete")
	public String delete(@RequestParam int pNum,
			@RequestParam int pcNum,
			@RequestParam String page,
			HttpSession session) throws Exception {
	
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		String query = "pcNum=" + pcNum + "&page=" + page;
		
		Map<String, Object> map = new HashMap<>();
		map.put("pNum", pNum);
		map.put("userId", info.getUserId());
	
		service.deleteProduct(pNum);
		
		return "redirect:/product/list?" + query;
	}
	
	// 게시글 신고
	@RequestMapping(value = "report")
	public String reportSubmit(ProductReport dto,
			@RequestParam int pNum,
			@RequestParam String page,
			@RequestParam int pcNum,
			HttpSession session) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			
			service.insertPreport(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/product/article?pNum=" + pNum + "&page=" + page + "&pcNum=" + pcNum;
	}
	
	@RequestMapping("updateDate")
	public String updateDate(
			@RequestParam int pNum,
			HttpSession session
			) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			service.updateDate(pNum, info.getUserId());
		} catch (Exception e) {
		}
		
		return "redirect:/product/list"; 
	}
}